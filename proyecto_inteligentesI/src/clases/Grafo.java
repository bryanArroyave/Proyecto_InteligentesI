package clases;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Grafo {

    private LinkedList<Nodo> V;
    private LinkedList<Arista> A;
    public int cant_bits;
    int Matriz[][];

    public static int maxPeso;
    public static int maxCantNodos;

    public Grafo() {
        cant_bits = 0;
        V = new LinkedList<>();
        A = new LinkedList<>();
        cambiarMatriz();

    }

    public void cambiarMatriz() {
        Matriz = new int[V.size()][V.size()];
        llenarMatriz();
        // mostrarInfo();
    }

    public void calcularCantidadBits() {
        cant_bits = (int) Math.ceil(Math.log(V.size()) / Math.log(2));
    }

    public boolean existeNodo(String binario) {
        for (Nodo nodo : V) {
            if (nodo.binario.equals(binario)) {
                return true;
            }
        }
        return false;
    }

    public void calcularmaxCantNodos() {

        this.maxCantNodos = this.V.size();

    }

    public void calcularMaxPeso() {
        int maxPeso = 10;
        this.maxPeso = (this.V.size() - 1) * maxPeso;
    }

    public int calcularMaximoBits() {
        return cant_bits * V.size();
    }

    private void llenarMatriz() {
        for (int i = 0; i < Matriz.length; i++) {
            for (int j = 0; j < Matriz[i].length; j++) {
                if (i != j) {
                    Arista arista = buscarArista("C" + (i), "C" + (j));
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
            dato += "C" + (i) + "\t";
        }

        for (int i = 0; i < V.size(); i++) {
            dato += "\nC" + (i);
            for (int j = 0; j < V.size(); j++) {
                dato += "\t" + Matriz[i][j];
            }
        }
        System.out.println("=================================================================================");
        System.out.println(dato);
        System.out.println("=================================================================================");
    }

    public Arista buscarArista(String o, String d) {
        for (Arista arista : A) {
            if (buscarNodo(o) == arista.origen && buscarNodo(d) == arista.destino) {
                return arista;
            }
        }
        return null;
    }

    public Nodo buscarNodoBinario(String binario) {
        for (Nodo nodo : V) {
            if (nodo.binario.equals(binario)) {
                return nodo;
            }
        }
        return null;
    }

    public Nodo buscarNodo(String nombre) {

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
