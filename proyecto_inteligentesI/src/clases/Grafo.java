package clases;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Grafo {

    private LinkedList<Nodo> V;
    private LinkedList<Arista> A;

    int Matriz[][];

    public Grafo() {
        V = new LinkedList<>();
        A = new LinkedList<>();
        cambiarMatriz();
    }

    public void cambiarMatriz() {
        Matriz = new int[V.size()][V.size()];
        llenarMatriz();
        mostrarInfo();
    }

    private void llenarMatriz() {
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[i].length; j++) {
                if (i != j) {
                    Arista arista = buscarArista("C" + (i + 1), "C" + (j + 1));
                    if (arista != null) {
                        Matriz[i][j] = arista.distancia;
                    }
                } else {
                    Matriz[i][j] = -1;
                }
            }
        }
    }

    public void mostrarInfo() {
        String dato = "\t";

        for (int i = 0; i < V.size(); i++) {
            dato += "C" + (i + 1) + "\t";
        }

        for (int i = 0; i < V.size(); i++) {
            dato += "\nC" + (i + 1);
            for (int j = 0; j < V.size(); j++) {
                dato += "\t" + Matriz[i][j];
            }
        }
        System.out.println("=================================================================================");
        System.out.println(dato);
        System.out.println("=================================================================================");
    }

    private Arista buscarArista(String o, String d) {
        for (Arista arista : A) {
            if (buscarNodo(o) == arista.origen && buscarNodo(d) == arista.destino) {
                return arista;
            }
        }
        return null;
    }

    private Nodo buscarNodo(String nombre) {

        for (Nodo nodo : V) {
            if (nodo.Nombre.equals(nombre)) {
                return nodo;
            }
        }
        return null;
    }

    public boolean puedeColocar(Rectangle r) {
        boolean puede = true;
        for (Nodo c : V) {

            puede = puede && c.comprobarUbicacion(r);
        }
        return puede;
    }

    /**
     * @return the V
     */
    public LinkedList<Nodo> getV() {
        return V;
    }

    /**
     * @param V the V to set
     */
    public void setV(LinkedList<Nodo> V) {
        this.V = V;
    }

    /**
     * @return the A
     */
    public LinkedList<Arista> getA() {
        return A;
    }

    /**
     * @param A the A to set
     */
    public void setA(LinkedList<Arista> A) {
        this.A = A;
    }

}
