package clases;

import java.util.Comparator;
import java.util.LinkedList;

public class Hijo2 {

    private String cromosoma;
    private String ruta;
    private LinkedList<Nodo> rutaN;
    private int peso;
    private float aptitud;
    private float evaluacion;

    public Hijo2(String cromosoma) {
        this.cromosoma = cromosoma;
        this.ruta = "";
        this.peso = 0;
        this.rutaN = new LinkedList<>();

    }

    public int compare(Hijo2 obj1, Hijo2 obj2) {
        Integer p1 = obj1.peso;
        Integer p2 = obj2.peso;

        if (p1 > p2) {
            return 1;
        } else if (p1 < p2) {
            return -1;
        } else {
            return 0;
        }
    }

    public void calcularEvaluacion() {

        float pesoNormalizado = ((float) peso / (float) Grafo.maxPeso);
        float visitadosNormalizado = (ruta.split("-").length) / (float) Grafo.maxCantNodos;

        evaluacion = (float) (pesoNormalizado * 0.3 + visitadosNormalizado * 0.7);

//        System.out.println(pesoNormalizado + "*0.3 + " + " " + visitadosNormalizado + "*0.7 = " + evaluacion);
    }

    private int calcularCantidadVisitados() {
        return 0;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    public float getAptitud() {
        return aptitud;
    }

    public void setAptitud(float aptitud) {
        this.aptitud = aptitud;

    }

    /**
     * @return the evaluacion
     */
    public float getEvaluacion() {
        return evaluacion;
    }

    /**
     * @param evaluacion the evaluacion to set
     */
    public void setEvaluacion(float evaluacion) {
        this.evaluacion = evaluacion;
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
//        this.ruta = this.actualizarInformacion();
    }

    /**
     * @return the ruta
     */
    public String getRuta() {
        return ruta;
    }

    /**
     * @param ruta the ruta to set
     */
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    /**
     * @return the rutaN
     */
    public LinkedList<Nodo> getRutaN() {
        return rutaN;
    }

    /**
     * @param rutaN the rutaN to set
     */
    public void setRutaN(LinkedList<Nodo> rutaN) {
        this.rutaN = rutaN;
    }

//    private String actualizarInformacion() {
//        int peso = Integer.parseInt(obtenerInformacion()[1]);
//        String ruta = obtenerInformacion()[0];
//
//        hijo.setPeso(peso);
//        hijo.setRuta(ruta);
//        hijo.calcularEvaluacion();F
//        return "";
//    }
    
}
