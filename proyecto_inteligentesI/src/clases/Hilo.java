package clases;

import GUI.panel_grafo;
import GUI.panel_principal;
import java.awt.Color;

public class Hilo implements Runnable {

    
    private final Thread thread;
    Algoritmos al;
    panel_principal pGrafo;
    public Grafo grafo;
    public boolean pintar;

    public Hilo(String ruta, Algoritmos p, panel_principal pGrafo) {
        this.al = p;
        this.grafo = panel_grafo.grafo;
        this.pGrafo = pGrafo;
        this.pintar = true;
        this.thread = new Thread(this);
        this.start();

    }
    private void start(){
        this.thread.start();
    }

    private String[] Desenrutar(String ruta) {
        if (!ruta.equals("")) {

            return ruta.substring(0, ruta.length() - 1).split(",");
        }
        return null;

    }

    public void ruta() {
        pGrafo.porcentaje.setText("Porcentaje: " + al.porcentaje + "% " + " iteración: " + al.itecacionActual);
        String ruta[];
        ruta = Desenrutar(al.rutaActual);
        for (Arista a : panel_grafo.grafo.getA()) {
            a.pintar = false;
            pGrafo.repaint();
        }
        if (ruta != null) {
            String o = "", d = "";
            for (int i = 0; i < ruta.length - 1; i++) {

                try {

                    o = grafo.buscarNodo(ruta[i]).Nombre;
                    d = grafo.buscarNodo(ruta[i + 1]).Nombre;
                } catch (Exception e) {
                }

                Arista arista = grafo.buscarArista(o, d);
                if (arista != null) {
                    if (!al.termino) {
                        arista.color = Color.RED;
                    } else {
                        if (al.itecacionActual != al.CANTIDADITERACIONES) {
                            arista.color = Color.GREEN;
                        } else {
                            arista.color = Color.BLUE;
                        }
                    }
                    arista.pintar = true;

                    pGrafo.p_grafo.repaint();
                }
            }

        }
    }

    @Override
    public void run() {

        while (pintar) {

            ruta();
        }

    }

}
