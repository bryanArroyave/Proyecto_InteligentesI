package clases;

import java.util.LinkedList;

public class Logica {

    private final int cant_hijos = 8;
    private Grafo G;
    LinkedList<String> hijos;

    public Logica(Grafo grafo) {
        hijos = new LinkedList<>();
        G = grafo;
    }

    public void generarHijos() {
        for (int i = 0; i < cant_hijos; i++) {
            String cromosoma = "";
            for (int j = 0; j < G.cant_bits * G.getV().size(); j++) {
                cromosoma += (int) (Math.round(Math.random()));
            }
            hijos.add(cromosoma);
        }
    }

    public int fitness(String hijo) {
        int cantNodosVisitados = cantNodosVisitados(hijo);

        return cantNodosVisitados; // + lo que se vaya a hacer;
    }

    private int cantNodosVisitados(String hijo) {
        int cantBits = G.cant_bits;
        int cantTotal = 0;
        for (int i = 0, j = 1; i < hijo.length(); i += cantBits, j++) {
            String nodoActual = "";
            for (int k = i; k < j * cantBits; k++) {
                nodoActual += hijo.charAt(k);
            }
            if (G.existeNodo(nodoActual)) {
                cantTotal++;
            }
        }
        return cantTotal;
    }
}
