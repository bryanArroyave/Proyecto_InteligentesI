package GUI;

import clases.Arista;
import clases.Grafo;
import clases.Nodo;
import clases.Opcion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class panel_grafo extends JPanel implements MouseListener, MouseMotionListener {

    public int ancho, alto, x, y;
    public boolean dibujando;

    private static int ID = 0;
    public static Grafo grafo;
    private Rectangle siguiente;
    public Color colorFondo;

    public panel_grafo(int ancho, int alto, int x, int y, int rand) {
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        this.dibujando = true;
        this.siguiente = new Rectangle();
        grafo = new Grafo();

        if (rand == 2) {
            colorFondo = new Color(2, 36, 64);

        } else {
            colorFondo = new Color(40, 41, 46);
        }
        configurarPanel();
        addMouseListener(this);
        addMouseMotionListener(this);

//        dibujarNodo(new Point(50, 50));
//        dibujarNodo(new Point(450, 50));
//        dibujarNodo(new Point(350, 450));
//        dibujarNodo(new Point(50, 350));
    }

    private void agregarArista(Nodo c) {
        for (Nodo nodo : grafo.getV()) {
            if (c != nodo) {
                int peso = obtenerPeso(Opcion.ALEATORIO, c, nodo);
                Arista a = new Arista(c, nodo, peso);
                grafo.getA().add(a);
                a = new Arista(nodo, c, peso);
                grafo.getA().add(a);
            }
        }
    }

    private int obtenerPeso(Opcion opcion, Nodo o, Nodo d) {

        if (Opcion.ALEATORIO == opcion) {
            return (int) (Math.random() * 10 + 1);
        } else if (Opcion.MANUAL == opcion) {
            return Integer.parseInt(
                    JOptionPane.showInputDialog(null, "ingres e peso de " + o.Nombre + " a " + d.Nombre));
        }
        return -1;

    }

    private void agregarNodo(Rectangle r) throws Exception {
        if (grafo.puedeColocar(r)) {
            Nodo c = new Nodo(r, "C" + ID);
            ID++;
            grafo.getV().add(c);
            agregarArista(c);
            repaint();
            // PanelInformacion.conti.setText(panel_info.conti.getText() + c.getNombre());
        } else {
            throw new Exception("No puede colocar la ciudad ");
        }

    }

    public void dibujar(Graphics g, Grafo grafo) {
        grafo.getA().forEach((a) -> {
            a.dibujar(g);
        });
        grafo.getV().forEach((c) -> {
            c.dibujar(g);
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        dibujar(g, grafo);

    }

    private void configurarPanel() {
        this.setSize(ancho, alto);
        this.setLocation(x, y);
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(colorFondo);
    }

    public void setSiguiente(Rectangle siguiente) {
        this.siguiente = siguiente;
    }

    private void dibujarNodo(Point punto) {
        Rectangle nuevo_nodo = new Rectangle(punto.x, punto.y, 50, 50);
        try {
            agregarNodo(nuevo_nodo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {

        if (me.getButton() == 1) {
            if (isDibujando()) {

                dibujarNodo(me.getPoint());
            }
        }
    }

    public void setDibujando(boolean dibujando) {
        this.dibujando = dibujando;
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {

    }

    public Rectangle getSiguiente() {
        return siguiente;
    }

    public boolean isDibujando() {
        return dibujando;
    }
}
