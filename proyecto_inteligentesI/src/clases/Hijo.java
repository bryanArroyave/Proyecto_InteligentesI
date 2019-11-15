package clases;

import java.util.Comparator;
import java.util.LinkedList;

public class Hijo implements Cloneable {

    private String cromosoma;
    private String ruta;
    private LinkedList<Nodo> rutaN;
    private int peso;
    private int aptitud;

    public Hijo(String cromosoma) {
        this.cromosoma = cromosoma;
        this.ruta = "";
        this.peso = 0;
        this.rutaN = new LinkedList<>();
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

    public int compare(Hijo obj1, Hijo obj2) {
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

    public int calcularAptitud() {
        return (int) (peso * 0.3 + 0.7 * (ruta.compareTo(",") + 1));
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

    /**
     *
     * @return @throws CloneNotSupportedException
     */
    /**
     * @return the aptitud
     */
    public int getAptitud() {
        return aptitud;
    }

    /**
     * @param aptitud the aptitud to set
     */
    public void setAptitud(int aptitud) {
        this.aptitud = aptitud;
    }

    @Override
    public Hijo clone() throws CloneNotSupportedException {
        Hijo hijo = (Hijo) super.clone();
        return hijo;
    }

}
