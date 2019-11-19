package clases;


import java.awt.Rectangle;
import java.util.LinkedList;

public class Grafo {

    private LinkedList<Nodo> V;
    private LinkedList<Arista> A;
    public int cant_bits;


    public static int maxPeso;
    public static int maxCantNodos;

    public Grafo() {
        cant_bits = 0;
        V = new LinkedList<>();
        A = new LinkedList<>();

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

        Grafo.maxCantNodos = this.V.size();

    }

    public void calcularMaxPeso() {
        int pesoMaximo = 10;
        Grafo.maxPeso = (this.V.size() - 1) * pesoMaximo;
    }

    public int calcularMaximoBits() {
        return cant_bits * V.size();
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
