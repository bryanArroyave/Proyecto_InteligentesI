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

    public String obtenerInfoFitness() {
        String dato = "<html><body> Hijos <br> <br>";
        for (String hijo : hijos) {
            System.out.println("hijo: " + hijo);
            dato += hijo + ": " + fitness(hijo) + " <br>";
        }
        return dato + "</body></html>";
    }

    public void generarHijos() {
        G.calcularCantidadBits();
        for (int i = 0; i < cant_hijos; i++) {
            String cromosoma = "";
            for (int j = 0; j < G.cant_bits * G.getV().size(); j++) {
                cromosoma += (int) (Math.round(Math.random()));
            }
            hijos.add(cromosoma);
        }
    }

    public void mostrarHijos() {
        System.out.println("=============================hijos========================");
        for (String hijo : hijos) {
            System.out.println(hijo);
        }
        System.out.println("==========================================================");
    }

    private int fitness(String hijo) {
        LinkedList<Nodo> cantNodosVisitados = nodosVisitados(hijo);

        return cantNodosVisitados.size(); // + lo que se vaya a hacer;
    }

    /*private int sacarPeso(LinkedList<Nodo> nodos) {
        Nodo nodoO = null;
        Nodo nodoD = null;

        if (nodos != null) {
            nodoO = nodos.get(0);
        }
    }*/
    private LinkedList<Nodo> nodosVisitados(String hijo) {
        int cantBits = G.cant_bits;

        LinkedList<Nodo> nodosVisitados = new LinkedList<>();

        for (int i = 0, j = 1; i < hijo.length(); i += cantBits, j++) {

            String nodoActual = "";
            for (int k = i; k < j * cantBits; k++) {
                nodoActual += hijo.charAt(k);
            }

            Nodo n;
            if ((n = G.buscarNodoBinario(nodoActual)) != null && !nodosVisitados.contains(n)) {
                System.out.println(nodoActual + " <- " + n.Nombre + " " + n.binario);
                nodosVisitados.add(n);
            } else {
                System.out.println(nodoActual + ": null");
            }
        }
        return nodosVisitados;
    }
}
