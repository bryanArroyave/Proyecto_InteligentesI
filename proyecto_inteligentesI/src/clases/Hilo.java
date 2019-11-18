package clases;

import static GUI.panel_grafo.grafo;
import GUI.panel_grafo;
import GUI.panel_informacion;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Hilo implements Runnable {

    private String ruta;
    private Thread thread;
    Algoritmos al;
    panel_informacion info;
    panel_grafo pGrafo;

    public Hilo(String ruta, Algoritmos p, panel_informacion pi, panel_grafo pGrafo) {
        this.ruta = ruta;
        this.al = p;
        this.info = pi;
        this.pGrafo = pGrafo;
        thread = new Thread(this);
        thread.start();
    }

    private String[] Desenrutar(String ruta) {
        if (!ruta.equals("")) {

            return ruta.substring(0, ruta.length() - 1).split(",");
        }
        return null;

    }

    @Override
    public void run() {
        JLabel a = info.adicionarLabel("porcentaje", new Rectangle(10, 10, 300, 100));
        JLabel a2 = info.adicionarLabel("ruta", new Rectangle(10, 30, 200, 100));
        String ruta[];
        while (true) {

            a.setText("Porcentaje: " + al.porcentaje + " %");
            ruta = Desenrutar(al.rutaActual);
            if (ruta != null) {
                a2.setText("ruta: " + Arrays.toString(ruta));
                String o = "", d = "";
                for (int i = 0; i < ruta.length - 1; i++) {
                    try {
                        o = grafo.buscarNodo(ruta[i]).Nombre;
                        d = grafo.buscarNodo(ruta[i + 1]).Nombre;

                        Arista arista = grafo.buscarArista(o, d);

                        if (arista != null) {
                            if (!al.termino) {
                                arista.color = Color.RED;
                            } else {
                                arista.color = Color.GREEN;
                            }
                            arista.pintar = true;
                            pGrafo.repaint();
                        }

                    } catch (Exception e) {

                    }
                }
                try {
                    Thread.sleep(500);
                    for (int i = 0; i < ruta.length - 1; i++) {
                        try {
                            o = grafo.buscarNodo(ruta[i]).Nombre;
                            d = grafo.buscarNodo(ruta[i + 1]).Nombre;

                            Arista arista = grafo.buscarArista(o, d);

                            if (arista != null) {

                                arista.pintar = false;
                                pGrafo.repaint();
                            }

                        } catch (Exception e) {

                        }
                    }
                } catch (InterruptedException ex) {

                }

            }

            info.repaint();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {

            }

        }

    }

}
