package GUI;

import clases.Arista;
import clases.Opcion;
import clases.Grafo;
import clases.Nodo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class panel_principal extends JPanel implements ActionListener {

    panel_informacion p_informacion;
    panel_grafo p_grafo;

    private JButton btn_agregarNodo;
    private JButton btn_solucionar;

    private final int ancho;
    private final int alto;

    public panel_principal(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;

        inicializarComponentes();
        configurarPanel();
        agregarComponentes();

    }

    private void inicializarComponentes() {

//================================PANELES===================================================
        p_grafo = new panel_grafo((3 * (int) ancho / 4) - 300, alto - 125, 50, 50);
        p_informacion = new panel_informacion((int) ancho / 4, alto, 3 * (int) ancho / 4, 0);
//================================BOTONES===================================================
        btn_agregarNodo = new JButton();
        btn_solucionar = new JButton();

        btn_agregarNodo.setBounds((3 * (int) ancho / 4) - 220, (alto / 2) - 50, 25, 25);
        btn_solucionar.setBounds((3 * (int) ancho / 4) - 220, (alto / 2) + 50, 25, 25);

        btn_agregarNodo.setBackground(Color.green);
        btn_solucionar.setBackground(Color.red);

        btn_agregarNodo.addActionListener(this);
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
        add(p_informacion);
        add(p_grafo);
        add(btn_agregarNodo);
        add(btn_solucionar);
    }

    private void agregarNodo() {
        p_grafo.dibujando = true;
    }

    private void solucionar() {

        btn_agregarNodo.setEnabled(false);
        btn_agregarNodo.setBackground(new Color(150, 150, 150));

        p_grafo.grafo.getV().forEach((nodo) -> {
            nodo.cambiarBinario(p_grafo.grafo.cant_bits);
        });
       
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_agregarNodo) {
            agregarNodo();
        } else if (e.getSource() == btn_solucionar) {
            solucionar();
        }
    }

}
