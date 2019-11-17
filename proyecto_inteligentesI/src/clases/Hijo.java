package clases;

import java.util.LinkedList;
import static GUI.panel_grafo.grafo;

public final class Hijo {

    private String cromosoma;
    private LinkedList<String> ruta;
    private float aptitud;
    private int peso;
    private float evaluacion;

    public Hijo(String cromosoma) {
        this.cromosoma = cromosoma;
        this.peso = 0;
        this.aptitud = 0;
        this.evaluacion = 0;
        this.ruta = new LinkedList<>();
        calcularRuta();
        calcularPeso();
        calcularEvalucion();
        obtenerInformacion();

    }

    private void calcularEvalucion() {
        evaluacion = 0f;
        float pesoNormalizado = ((float) this.peso / (float) grafo.maxPeso);
        float visitadosNormalizado = ((float) this.ruta.size()) / (float) grafo.maxCantNodos;
        this.evaluacion = (float) (pesoNormalizado*0.10 + visitadosNormalizado*0.90);

    }

    private void calcularPeso() {
        String o = "", d = "";
        peso = 0;
        for (int i = 0; i < this.ruta.size() - 1; i++) {
            o = grafo.buscarNodo(this.ruta.get(i)).Nombre;
            d = grafo.buscarNodo(this.ruta.get(i + 1)).Nombre;

            this.peso += grafo.buscarArista(o, d).distancia;

        }

    }

    private void calcularRuta() {
        LinkedList<String> visitados = new LinkedList<>();
        ruta.clear();
        for (int i = 0, j = 1; i < grafo.calcularMaximoBits(); i += grafo.cant_bits, j++) {
            String nodoActual = "";

            for (int k = i; k < j * grafo.cant_bits; k++) {

                nodoActual += cromosoma.charAt(k);
            }
            if (!visitados.contains(nodoActual) && grafo.existeNodo(nodoActual)) {
                visitados.add(nodoActual);

                ruta.add(grafo.buscarNodoBinario(nodoActual).Nombre);

            }
        }
    }

    /**
     * @return the cromosoma
     */
    public String getCromosoma() {
        return cromosoma;
    }

    /**
     * @param cromosoma the cromosoma to set
     */
    public void setCromosoma(String cromosoma) {
        this.cromosoma = cromosoma;
        calcularRuta();
        calcularPeso();
        calcularEvalucion();
        obtenerInformacion();
    }

    /**
     * @return the ruta
     */
    public String getRuta() {

        String dato = "|";
        for (String s : ruta) {
            dato = dato + s + "|";

        }
        return dato;
    }

    /**
     * @return the aptitud
     */
    public float getAptitud() {
        return aptitud;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @return the evaluacion
     */
    public float getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param aptitud the aptitud to set
     */
    public void setAptitud(float aptitud) {
        this.aptitud = aptitud;
    }

    public void obtenerInformacion() {
        String dato = "================= dato Hijo " + cromosoma + " ======================="
                + "\naptitud: " + aptitud //                + "\npeso " + peso
                //                + "\nevaluacion: " + evaluacion
                //                + "\nruta " + ruta;
                ;
        //System.out.println(dato);
    }
}
