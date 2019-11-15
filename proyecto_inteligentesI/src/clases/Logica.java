package clases;

import GUI.panel_informacion;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logica implements Runnable {

    private final int cantHijos = 8;
    private final float porcentajeMutacion = 0.3f;

    boolean reepintar;
    Thread thread;

    private int cantIteraciones = 15;
    private final Grafo G;
    LinkedList<Hijo> hijos;
    LinkedList<Nodo> nodosVisitados;
    panel_informacion p_informacion;
    String infoFitness;

    public Logica(Grafo grafo, panel_informacion p_informacion) {
        this.p_informacion = p_informacion;
        this.reepintar = false;
        hijos = new LinkedList<>();
        nodosVisitados = new LinkedList<>();
        G = grafo;
        infoFitness = "";
        thread = new Thread(this);
    }

    private void seleccionar() {

    }

    public void algoritmoGenetico() {
        generarHijos();
        //mostrarHijos();

        while (!terminar()) {
            infoFitness = obtenerInfoFitness();

            System.out.println(infoFitness);
            //================================Mostrar datos en el panel====================================
            reepintar = true;
            //=============================================================================================
            LinkedList<Hijo> seleccionados = this.seleccion();
            //=============================================================================================
            LinkedList<Hijo> aptos = this.seleccionReproduccion(seleccionados);
            LinkedList<Hijo> clones = this.reproduccion(aptos);
            LinkedList<Hijo> Cruzados = this.crossover(aptos, clones);
            LinkedList<Hijo> mutados = this.mutacion(Cruzados);

            hijos = mutados;

        }
    }

    private boolean terminar() {

        if (porcentajeCoincidencias() >= 90 || cantIteraciones == 0) {
            return true;
        }
        cantIteraciones--;
        return false;
    }

    private float porcentajeCoincidencias() {
        int max = hijos.get(0).getAptitud();
        int t_coincicencia;
        for (int i = 1; i < hijos.size(); i++) {
            if ((t_coincicencia = totalCoincidencia(hijos.get(i))) > max) {
                max = t_coincicencia;
            }
        }
        return max * 100 / 8;
    }

    private int totalCoincidencia(Hijo hijo) {

        int totalCoincidencia = 0;
        for (Hijo h : hijos) {
            if (h != hijo) {
                if (h.getAptitud() == hijo.getAptitud()) {
                    totalCoincidencia++;
                }
            }
        }
        return totalCoincidencia;
    }

    private String obtenerInfoFitness() {
        String dato = "<html><body> Hijos <br> <br>";
        String dato2 = "";
        for (Hijo hijo : hijos) {
            nodosVisitados = nodosVisitados(hijo.getCromosoma());
            int peso = Integer.parseInt(obtenerInformacion()[1]);
            String ruta = obtenerInformacion()[0];
            hijo.setPeso(peso);
            hijo.setRuta(ruta);
            hijo.calcularAptitud();

            dato += hijo.getCromosoma() + " " + nodosVisitados.size() + " -> " + ruta + "->" + peso + " <br>";
            dato2 += hijo.getCromosoma() + " " + nodosVisitados.size() + " -> " + ruta + "->" + peso + " \n";

        }
        System.out.println("==========================================");

        System.out.println(dato2);
        System.out.println("==========================================");
        return dato + "</body></html>";
    }

    private void ordenarAptitud() {
        Collections.sort(hijos, (a, b) -> a.getAptitud() < b.getAptitud() ? 1 : a.getAptitud() == b.getAptitud() ? 0 : -1);
    }

    private String[] obtenerInformacion() {

        String[] informacion = new String[2];
        String ruta = "";
        int peso = 0;
        if (nodosVisitados.size() > 1) {
            for (int i = 0; i < nodosVisitados.size() - 1; i++) {
                Arista a = G.buscarArista(nodosVisitados.get(i).Nombre, nodosVisitados.get(i + 1).Nombre);
                ruta = ruta + ("[" + a.origen.Nombre + "," + a.destino.Nombre + "]");
                peso += a.distancia;

            }
        }

        informacion[0] = ruta;
        informacion[1] = String.valueOf(peso);

        return informacion;
    }

    private void generarHijos() {
        G.calcularCantidadBits();
        for (int i = 0; i < cantHijos; i++) {
            String cromosoma = "";
            for (int j = 0; j < G.cant_bits * G.getV().size(); j++) {
                cromosoma += (int) (Math.round(Math.random()));
            }
            hijos.add(new Hijo(cromosoma));
        }

    }

    private void mostrarHijos() {
        System.out.println("=============================hijos========================");
        for (Hijo hijo : hijos) {
            System.out.println(hijo.getCromosoma());
        }
        System.out.println("==========================================================");
    }

    private LinkedList<Nodo> nodosVisitados(String hijo) {
        int cantBits = G.cant_bits;
        LinkedList<Nodo> nodosVisitados = new LinkedList<>();
        for (int i = 0, j = 1; i < hijo.length(); i += cantBits, j++) {
            String nodoActual = "";
            for (int k = i; k < j * cantBits; k++) {
                nodoActual += hijo.charAt(k);
            }
            try {
                adicionarVisitados(G.buscarNodoBinario(nodoActual), nodoActual, nodosVisitados);
            } catch (Exception e) {

            }
        }
        return nodosVisitados;
    }

    private void adicionarVisitados(Nodo n, String nodoActual, LinkedList<Nodo> nodosVisitados) throws Exception {
        if (n != null) {
            if (!nodosVisitados.contains(n)) {
                nodosVisitados.add(n);
            } else {
                throw new Exception("ya contiene ese nodo");
            }
        } else {
            throw new Exception("No existe ese nodo");
        }
    }

    private LinkedList<Hijo> seleccion() {
        //Se eligen los individuos con el fitness más alto
        //se requieren mas de dos individuos, para este caso escogemos 3
        this.ordenarAptitud();
        LinkedList<Hijo> seleccionados = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            seleccionados.add(hijos.get(i));
        }
        return seleccionados;
    }

    private LinkedList<Hijo> seleccionReproduccion(LinkedList<Hijo> hijosSeleccionados) {
        Random rand = new Random();
        LinkedList<Hijo> hijosReproduccion = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            int aleatorio = rand.nextInt(hijosSeleccionados.size());
            Hijo hijo = hijosSeleccionados.get(aleatorio);
            hijosReproduccion.add(hijo);
            hijosSeleccionados.remove(hijo);
        }
        return hijosReproduccion;
    }

    private LinkedList<Hijo> clonar(Hijo hijo) throws CloneNotSupportedException {
        LinkedList<Hijo> clones = new LinkedList<>();
        for (int i = 0; i < this.cantHijos / 2; i++) {
            clones.add(hijo.clone());
        }
        return clones;
    }

    private LinkedList<Hijo> reproduccion(LinkedList<Hijo> hijosReproduccion) {
        //Se crea una nueva poblacion copiando los "padres"
        LinkedList<Hijo> hijosClonados = new LinkedList<>();
        hijosReproduccion.forEach((hijo) -> {
            try {
                hijosClonados.addAll(this.clonar(hijo));
            } catch (CloneNotSupportedException ex) {
            }
        });
        return hijosClonados;
    }

    private int posicionRandom() {
        return (int) (Math.random() * this.G.calcularMaximoBits() - 1) + 1;
    }

    private LinkedList<Hijo> crossover(LinkedList<Hijo> padres, LinkedList<Hijo> hijosClonados) {
        //Se mezclan las caracteristicas de los padres entre los individuos de la poblacion

        for (Hijo hijosClonado : hijosClonados) {
            int posRandom = posicionRandom();

            String nuevoCromosoma = "";

            for (int i = 0; i < posRandom; i++) {
                nuevoCromosoma += padres.get(0).getCromosoma().charAt(i);
            }
            for (int i = posRandom; i < this.G.calcularMaximoBits(); i++) {
                nuevoCromosoma += padres.get(1).getCromosoma().charAt(i);
            }

            hijosClonado.setCromosoma(nuevoCromosoma);
        }
        return hijosClonados;

    }

    private LinkedList<Hijo> mutacion(LinkedList<Hijo> nuevaGeneracion) {
        //Se añaden algunas variaciones al azar que no estaban en los padres
        //porcentaje de mutacion entre 20 y 30%
        //De acá vuelve y se llama a la funcion fitness
        for (int i = 0; i < nuevaGeneracion.size(); i++) {

            double probabilidad = (Math.random());

            if (probabilidad <= porcentajeMutacion) {
                String nuevoCromosoma = nuevaGeneracion.get(i).getCromosoma();
                int posAleatoria = (int) (Math.random() * this.G.calcularMaximoBits());
                int gen = nuevoCromosoma.charAt(posAleatoria);

                int nuevoGen = 1 - gen;

                nuevoCromosoma = reemplazarBit(nuevoCromosoma, posAleatoria);
                nuevaGeneracion.get(i).setCromosoma(nuevoCromosoma);
            }
        }
        return nuevaGeneracion;

    }

    private String reemplazarBit(String nuevoCromosoma, int posAleatoria) {
        char[] tempCharArray = nuevoCromosoma.toCharArray();
        tempCharArray[posAleatoria] = '1'; // Donde 'x' es la posición que buscas.
        return String.valueOf(tempCharArray);
    }

    @Override
    public void run() {
        while (true) {
            if (reepintar) {
                p_informacion.removeAll();
                p_informacion.adicionarLabel(infoFitness, new Rectangle(10, 50, 1000, 200));
            }
            reepintar = false;

        }
    }

}
