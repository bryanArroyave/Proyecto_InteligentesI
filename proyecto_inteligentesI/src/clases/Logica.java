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

    private int cantIteraciones = 1000;
    private final Grafo G;
    LinkedList<Hijo2> hijos;
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

    public void algoritmoGenetico() {
        generarHijos();

        while (true) {
            LinkedList<Hijo2> seleccionados = seleccionar(hijos);
            if (terminar()) {
                break;
            }
            System.out.println("");
            System.out.println("========================Seleccionados========================");
            for (Hijo2 s : seleccionados) {
                System.out.println(s.getCromosoma() + " " + s.getRuta() + " " + s.getAptitud());
            }
            System.out.println("=============================================================");
            System.out.println("");
            LinkedList<Hijo2> cruzados = cruzar(seleccionados);
            System.out.println("");
            System.out.println("=========================Cruzados===========================");
            for (Hijo2 s : cruzados) {
                System.out.println(s.getCromosoma() + " " + s.getRuta() + " " + s.getAptitud());
            }
            System.out.println("=============================================================");
            System.out.println("");
            LinkedList<Hijo2> mutados = mutar(cruzados);
            System.out.println("");
            System.out.println("=========================mutados===========================");
            for (Hijo2 s : mutados) {
                System.out.println(s.getCromosoma() + " " + s.getRuta() + " " + s.getAptitud());
            }
            System.out.println("=============================================================");
            System.out.println("");
            hijos = mutados;
        }
    }

    private LinkedList<Hijo2> mutar(LinkedList<Hijo2> cruzados) {
        //Se añaden algunas variaciones al azar que no estaban en los padres
        //porcentaje de mutacion entre 20 y 30%
        //De acá vuelve y se llama a la funcion evaluacion
        for (int i = 0; i < cruzados.size(); i++) {

            double probabilidad = (Math.random());

            if (probabilidad <= porcentajeMutacion) {
                String nuevoCromosoma = cruzados.get(i).getCromosoma();
                int posAleatoria = (int) (Math.random() * this.G.calcularMaximoBits());
                int gen = nuevoCromosoma.charAt(posAleatoria);

                int nuevoGen = 1 - gen;

                nuevoCromosoma = reemplazarBit(nuevoCromosoma, posAleatoria);
                cruzados.get(i).setCromosoma(nuevoCromosoma);
            }

            String info[] = obtenerInformacion();
            cruzados.get(i).setPeso(Integer.parseInt(obtenerInformacion()[1]));
            cruzados.get(i).setRuta(info[0]);

        }
        return cruzados;

    }

    private LinkedList<Hijo2> cruzar(LinkedList<Hijo2> seleccionados) {

        for (int i = 0; i < cantHijos; i += 2) {
            Hijo2 padre1 = seleccionados.get(i);
            Hijo2 padre2 = seleccionados.get(i + 1);

            int posRandom = posicionRandom();

            String nuevoCromosoma1 = "";
            String nuevoCromosoma2 = "";

            for (int j = 0; j < posRandom; j++) {
                nuevoCromosoma1 += padre1.getCromosoma().charAt(j);
                nuevoCromosoma2 += padre2.getCromosoma().charAt(j);
            }
            for (int j = posRandom; j < this.G.calcularMaximoBits(); j++) {
                nuevoCromosoma1 += padre2.getCromosoma().charAt(j);
                nuevoCromosoma2 += padre1.getCromosoma().charAt(j);
            }

            seleccionados.get(i).setCromosoma(nuevoCromosoma1);
            seleccionados.get(i + 1).setCromosoma(nuevoCromosoma2);

            String info[] = obtenerInformacion();
            seleccionados.get(i).setPeso(Integer.parseInt(obtenerInformacion()[1]));
            seleccionados.get(i).setRuta(info[0]);

        }
        return seleccionados;
    }

    private void calcularAptitud() {
        for (Hijo2 hijo : hijos) {
            hijo.setAptitud(hijo.getEvaluacion() / this.sacarPromedio());
        }
    }

    private LinkedList<Hijo2> adicionarEnteros(LinkedList<Hijo2> seleccionados) {
        for (Hijo2 hijo : hijos) {
            for (int i = 0; i < (int) hijo.getAptitud(); i++) {
                seleccionados.add(hijo);
            }
        }
        return seleccionados;
    }

    private LinkedList<Hijo2> adicionarDecimal(LinkedList<Hijo2> seleccionados) {
        int tamNuevos = seleccionados.size();
        for (int i = 0; i < cantHijos - tamNuevos; i++) {
            int pos = escogerHijoRuleta(llenarRuleta());
            seleccionados.add(hijos.get(pos));
        }
        return seleccionados;
    }

    private LinkedList<Hijo2> seleccionar(LinkedList<Hijo2> hijos) {
        LinkedList<Hijo2> seleccionados = new LinkedList<>();
        funcionEvaluacion(hijos);
        this.ordenarEvaluacion();

        this.calcularAptitud();

        /*     for (Hijo2 hijo : hijos) {
            System.out.println(hijo.getCromosoma() + " " + hijo.getAptitud());
        }*/
        seleccionados = adicionarEnteros(seleccionados);
        seleccionados = adicionarDecimal(seleccionados);

        return seleccionados;
    }

    private float[] crearRuleta() {
        float ruleta[] = new float[cantHijos];

        return ruleta;
    }

    private float obtenerDecimal(float numero) {
        String str = String.valueOf(numero);
        return Float.parseFloat(str.substring(str.indexOf('.')));
    }

    private float[] llenarRuleta() {
        float ruleta[] = crearRuleta();

        ruleta[0] = obtenerDecimal(hijos.get(0).getEvaluacion());
        for (int i = 1; i < ruleta.length; i++) {
            ruleta[i] = ruleta[i - 1] + obtenerDecimal(hijos.get(i).getEvaluacion());
        }
        String dato = "";
        for (int i = 0; i < ruleta.length; i++) {
            dato += "|" + ruleta[i] + "|";
        }
//        System.out.println("=======================================");
//        System.out.println(dato);
        return ruleta;
    }

    private int escogerHijoRuleta(float[] ruleta) {

        int posicion = 0;
        float aleatorio = (float) (Math.random() * ruleta[ruleta.length - 1]);
//        System.out.println("Aleatorio " + aleatorio);
        for (int i = 0; i < ruleta.length - 1; i++) {
            if (aleatorio > ruleta[i + 1]) {
                posicion = i + 1;
            }
        }

        return posicion;
    }

    private boolean terminar() {
        System.out.println("Porcentaje " + porcentajeCoincidencias() + "%");
        if (porcentajeCoincidencias() >= 90 || cantIteraciones == 0) {
            return true;
        }
        cantIteraciones--;
        return false;
    }

    private float porcentajeCoincidencias() {
        float max = hijos.get(0).getEvaluacion();
        int t_coincicencia = 0;

        for (int i = 1; i < hijos.size(); i++) {
            if (hijos.get(i).getEvaluacion() > max) {
                max = hijos.get(i).getEvaluacion();
            }
        }
        if (max != 0) {
            for (int i = 0; i < hijos.size(); i++) {
                if (hijos.get(i).getEvaluacion() == max) {
                    t_coincicencia++;
                }
            }
            return t_coincicencia * 100 / cantHijos;
        } else {
            return 0;
        }
    }

    private int totalCoincidencia(Hijo2 hijo) {

        int totalCoincidencia = 0;
        for (Hijo2 h : hijos) {
            if (h != hijo) {
                if (h.getAptitud() == hijo.getAptitud()) {
                    totalCoincidencia++;
                }
            }
        }
        return totalCoincidencia;
    }

    private String funcionEvaluacion(LinkedList<Hijo2> hijos) {
        String dato = "<html><body> Hijos <br> <br>";
        String dato2 = "";

        for (Hijo2 hijo : hijos) {
            nodosVisitados = nodosVisitados(hijo.getCromosoma());

          

//            dato += hijo.getCromosoma() + " " + nodosVisitados.size() + " -> " + ruta + "->" + peso + " <br>";
//            dato2 += hijo.getCromosoma() + " " + nodosVisitados.size() + " -> " + ruta + "->" + peso + " \n";

        }
//        System.out.println("==========================================");
//
//        System.out.println(dato2);
//        System.out.println("==========================================");
        return dato + "</body></html>";
    }

    private void ordenarEvaluacion() {
        Collections.sort(hijos, (a, b) -> a.getEvaluacion() < b.getEvaluacion() ? 1 : a.getEvaluacion() == b.getEvaluacion() ? 0 : -1);
    }

    private String[] obtenerInformacion() {

        String[] informacion = new String[2];
        String ruta = "";
        int peso = 0;
        if (nodosVisitados.size() > 1) {

            for (int i = 0; i < nodosVisitados.size() - 1; i++) {
                Arista a = G.buscarArista(nodosVisitados.get(i).Nombre, nodosVisitados.get(i + 1).Nombre);
                if (i == 0) {
                    ruta = a.origen.Nombre;
                }

                ruta = ruta + "-" + a.destino.Nombre;
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
            hijos.add(new Hijo2(cromosoma));
        }

    }

    private void mostrarHijos() {
//        System.out.println("=============================hijos========================");
//        for (Hijo2 hijo : hijos) {
//            System.out.println(hijo.getCromosoma());
//        }
//        System.out.println("==========================================================");
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

    private float sacarPromedio() {
        float promedio = 0f;

        for (Hijo2 hijo : hijos) {
            promedio += hijo.getEvaluacion();
        }

        return promedio / hijos.size();
    }

    private int posicionRandom() {
        return (int) (Math.random() * this.G.calcularMaximoBits() - 1) + 1;
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
