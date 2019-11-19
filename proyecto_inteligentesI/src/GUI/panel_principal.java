package GUI;

import clases.Algoritmos;
import clases.Archivo;
import clases.Arista;
import clases.Hilo;
import clases.Imagen;
import clases.Nodo;
import clases.Sonido;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class panel_principal extends JPanel implements ActionListener, Runnable {

    public panel_grafo p_grafo;
    ImageIcon fondo;
    public JLabel porcentaje;
    private JButton btn_solucionar;
    private JLabel label;
    private final int ancho;
    private final int alto;
    Algoritmos al;
    Hilo h;
    private final int rand;

    public panel_principal(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
        porcentaje = new JLabel("Porcentaje: 0%");
        configurarLabel(porcentaje, new Rectangle(50, 25, ancho, 25));

        rand = (int) (Math.random() * 2 + 1);
        this.fondo = new ImageIcon(getClass().getResource("../Recursos/fondo" + rand + ".gif"));
        inicializarComponentes();
        configurarPanel();
        agregarComponentes();

    }

    private void configurarLabel(JLabel label, Rectangle r) {
        label.setFont(new Font("Arial", 1, 15));
        label.setForeground(Color.white);
        label.setBounds(r);
        label.setVisible(true);
        add(label);
        repaint();
    }

    private void inicializarComponentes() {

//================================PANELES===================================================
        p_grafo = new panel_grafo(ancho - 300, alto - 125, 50, 50, rand);
//================================BOTONES===================================================

        btn_solucionar = new JButton();

        btn_solucionar.setBounds(ancho - 230, 125, 25, 25);
        label = new JLabel("Solucionar");
        label.setFont(new Font("Arial", 1, 15));
        label.setForeground(Color.white);
        label.setBounds(ancho - 200, 125, 300, 25);
        label.setVisible(true);

        btn_solucionar.setBackground(Color.red);

        btn_solucionar.addActionListener(this);

    }

    private void configurarPanel() {
        this.setSize(ancho, alto);
        this.setLocation(0, 0);
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(Color.black);
    }

    private void agregarComponentes() {
        add(p_grafo);
        add(btn_solucionar);
        add(label);
    }

    private void agregarNodo() {
        p_grafo.dibujando = true;
    }

    private void solucionar() {
        Imagen imagen2 = new Imagen(p_grafo, "grafo", "src/Consolidado");
        imagen2.dibujarImagen(panel_grafo.grafo);
        panel_grafo.grafo.calcularMaxPeso();
        panel_grafo.grafo.calcularmaxCantNodos();

        btn_solucionar.setBackground(Color.green);
        panel_grafo.grafo.calcularCantidadBits();
        panel_grafo.grafo.getV().forEach((nodo) -> {
            nodo.cambiarBinario(panel_grafo.grafo.cant_bits);
        });

        if (panel_grafo.grafo.getV().size() > 3) {

            btn_solucionar.setEnabled(false);
            p_grafo.dibujando = false;
            for (Arista a : panel_grafo.grafo.getA()) {
                a.pintar = false;
                repaint();
            }
            al = new Algoritmos();
            h = new Hilo("", al, this);

            Thread t = new Thread(al);
            t.start();
            new Thread(this).start();

        } else {
            JOptionPane.showMessageDialog(this, "Cantidad de ciudades insufucientes ", " ERROR ", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btn_solucionar) {
            solucionar();
        }
    }

    private String[] Desenrutar(String ruta) {
        if (!ruta.equals("")) {

            return ruta.substring(0, ruta.length() - 1).split(",");
        }
        return null;

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(fondo.getImage(), 0, 0, ancho, alto, this);
        repaint();
    }

    private void pintarGrafoFinal(String laRuta) {
        String[] ruta = Desenrutar(laRuta);
        String o = "", d = "";
        porcentaje.setText("Porcentaje: " + al.porcentaje + "% " + " iteración: " + al.itecacionActual);
        for (Arista a : panel_grafo.grafo.getA()) {
            a.pintar = false;
            p_grafo.repaint();
        }
        for (int i = 0; i < ruta.length - 1; i++) {
            try {
                o = panel_grafo.grafo.buscarNodo(ruta[i]).Nombre;
                d = panel_grafo.grafo.buscarNodo(ruta[i + 1]).Nombre;

                Arista a = p_grafo.grafo.buscarArista(o, d);

                if (a != null) {
                    a.pintar = true;
                    p_grafo.repaint();
                    if (al.itecacionActual != al.CANTIDADITERACIONES) {
                        a.color = Color.GREEN;
                    } else {
                        a.color = new Color(52, 186, 186);
                    }
                }

            } catch (Exception e) {
            }
        }
    }

    private String calcularCantNodosNoVisitados(String[] ruta) {
        String dato = "";
        int cant = 0;
        for (Nodo nodo : panel_grafo.grafo.getV()) {
            boolean existe = false;
            for (String r : ruta) {
                if (nodo.Nombre.equals(r)) {
                    existe = true;
                }
            }
            if (!existe) {
                dato += nodo.Nombre + " ";
                cant++;
            }
        }
        return "[" + cant + "] " + dato;
    }

    @Override
    public void run() {

        while (!al.termino) {
            System.out.print("");
        }

        al.termino = true;
        h.pintar = false;
        pintarGrafoFinal(al.rutaActual);

        Imagen imagen = new Imagen(p_grafo, "grafoFinal", "src/Consolidado");
        imagen.dibujarImagen(panel_grafo.grafo);
        al.calcularCabecera();
        System.out.println("dato " + calcularCantNodosNoVisitados(Desenrutar(al.rutaActual)));

        String noVisitados = calcularCantNodosNoVisitados(Desenrutar(al.rutaActual));
        String informacion = al.informacion
                + "\n\nRUTA: " + Arrays.toString(Desenrutar(al.rutaActual))
                + "\n\nNODOS NO VISITADOS: " + noVisitados;
        Archivo archivo = new Archivo(informacion, al.cabecera, "src/Consolidado/genetico.txt");
        archivo.LimpiarTxt();
        archivo.guardarTxt();

        if (JOptionPane.showConfirmDialog(null, "¿Desea ver los resultados consolidados?", "INFORMACION", JOptionPane.YES_NO_OPTION) == 0) {
            archivo.Execute();
        }
    }

}
