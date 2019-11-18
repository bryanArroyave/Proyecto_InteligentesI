package clases;

import GUI.panel_informacion;
import java.util.LinkedList;
import static GUI.panel_grafo.grafo;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Genetico {

    private final int cantHijos = 20;
    private final float porcentajeMutacion = 0.3f;
    private int cantIteraciones = 1000;

    private LinkedList<Hijo> hijos;
    private LinkedList<Hijo> nuevaGeneracion;

    public Genetico() {
        hijos = new LinkedList<>();
        nuevaGeneracion = new LinkedList<>();
    }

    public void algoritmoGenetico() {
        generarHijos();

        while (cantIteraciones > 0) {

            seleccionar();
            cruzar();
            if (terminar()) {
                hijos = (LinkedList<Hijo>) nuevaGeneracion.clone();
                break;
            }
            mutar();

            hijos = (LinkedList<Hijo>) nuevaGeneracion.clone();
            nuevaGeneracion.clear();
        }
        for (Hijo hijo : hijos) {
            System.out.println(hijo.getRuta());

        }
    }

    private void generarHijos() {
        grafo.calcularCantidadBits();

        for (int i = 0; i < cantHijos; i++) {
            String cromosoma = "";
            for (int j = 0; j < grafo.cant_bits * grafo.getV().size(); j++) {
                cromosoma += (int) (Math.round(Math.random()));
            }
            System.out.println(cromosoma);

            hijos.add(new Hijo(cromosoma));
        }

    }

    private void seleccionar() {
        calcularAptitud();
        adicionarEnteros();
        adicionarDecimal();

    }

    private void cruzar() {

        LinkedList<Hijo> auxiliar = (LinkedList<Hijo>) this.nuevaGeneracion.clone();

        for (int i = 0; i < cantHijos; i += 2) {
            String c1 = "";
            String c2 = "";

            int posRandom = (int) (Math.random() * grafo.calcularMaximoBits() - 1) + 1;

            String h1 = nuevaGeneracion.get(i).getCromosoma();
            String h2 = nuevaGeneracion.get(i + 1).getCromosoma();
            c1 = h1.substring(0, posRandom) + h2.substring(posRandom, h2.length());
            c2 = h2.substring(0, posRandom) + h1.substring(posRandom, h1.length());

            auxiliar.get(i).setCromosoma(c1);
            auxiliar.get(i + 1).setCromosoma(c2);
//            System.out.println("=======================Random");
//            System.out.println(posRandom);
//            System.out.println(h1 + " " + c1);
//            System.out.println(h2 + " " + c2);
//
//            System.out.println("=======================fin Random");

        }
        nuevaGeneracion = auxiliar;

    }

    private String reemplazarBit(String nuevoCromosoma, int posAleatoria, int nuevoGen) {

        String dato = "";

        for (int i = 0; i < nuevoCromosoma.length(); i++) {

            if (i == posAleatoria) {
                dato += nuevoGen;
            } else {
                dato += nuevoCromosoma.charAt(i);
            }
        }

        return dato;
    }

    private void mutar() {

        for (int i = 0; i < nuevaGeneracion.size(); i++) {

            double probabilidad = (Math.random());

            if (probabilidad <= porcentajeMutacion) {
                String nuevoCromosoma = nuevaGeneracion.get(i).getCromosoma();
                int posAleatoria = (int) (Math.random() * grafo.calcularMaximoBits());

                char gen = nuevoCromosoma.charAt(posAleatoria);

                int nuevoGen = 1 - Integer.parseInt(gen + "");

                nuevoCromosoma = reemplazarBit(nuevoCromosoma, posAleatoria, nuevoGen);
                nuevaGeneracion.get(i).setCromosoma(nuevoCromosoma);

            }

        }
    }
//====================================== métodos de terminar ======================================

    private boolean terminar() {
        System.out.println("Porcentaje " + porcentajeCoincidencias() + "%");
        if (porcentajeCoincidencias() >= 85) {
          
            return true;
        }
        cantIteraciones--;
        return false;
    }

    private float porcentajeCoincidencias() {

        int total = 0;
        char[] datos = nuevaGeneracion.get(0).getCromosoma().toCharArray();

        boolean estado = true;
       
        for (int i = 0; i < grafo.calcularMaximoBits(); i++) {

            for (Hijo hijo : hijos) {
                estado = estado && (hijo.getCromosoma().charAt(i) == datos[i]);

            }
            if (estado) {
                total++;
            }
        }

        return total * 100 / grafo.calcularMaximoBits();//        float max = nuevaGeneracion.get(0).getAptitud();
        //        int t_coincicencia = 0;
        //
        //        for (int i = 1; i < nuevaGeneracion.size(); i++) {
        //            if (nuevaGeneracion.get(i).getAptitud() > max) {
        //                max = nuevaGeneracion.get(i).getAptitud();
        //            }
        //        }
        //        if (max != 0) {
        //            for (int i = 0; i < nuevaGeneracion.size(); i++) {
        //                if (nuevaGeneracion.get(i).getAptitud() == max) {
        //                    t_coincicencia++;
        //                }
        //            }
        //            return t_coincicencia * 100 / cantHijos;
        //        } else {
        //            return 0;
        //        }

    }
//====================================== fin de terminar =========================================
//====================================== métodos de aptitud ======================================

    private void calcularAptitud() {
        float promedio = this.promedioEvaluaciones();

        for (Hijo hijo : hijos) {
            hijo.setAptitud(hijo.getEvaluacion() / promedio);

            hijo.obtenerInformacion();
        }
    }

    private float promedioEvaluaciones() {
        float sumaEvaluaciones = 0f;
        for (Hijo hijo : hijos) {
            sumaEvaluaciones += hijo.getEvaluacion();

        }
        return (float) (sumaEvaluaciones / hijos.size());
    }
//========================================= fin de aptitud ========================================
//================================= Métodos de seleccion Ruleta ===================================

    private void adicionarDecimal() {
        int tamNuevos = nuevaGeneracion.size();
        for (int i = 0; i < cantHijos - tamNuevos; i++) {
            int pos = escogerHijoRuleta(llenarRuleta());
            nuevaGeneracion.add(hijos.get(pos));
        }
    }

    private void adicionarEnteros() {

        for (Hijo hijo : hijos) {
            for (int i = 0; i < (int) hijo.getAptitud(); i++) {
                nuevaGeneracion.add(hijo);
            }
        }
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

    private float obtenerDecimal(float numero) {
        String str = String.valueOf(numero);
        return Float.parseFloat(str.substring(str.indexOf('.')));
    }

    private float[] llenarRuleta() {
        float ruleta[] = new float[cantHijos];

        ruleta[0] = obtenerDecimal(hijos.get(0).getEvaluacion());
        for (int i = 1; i < ruleta.length; i++) {
            ruleta[i] = ruleta[i - 1] + obtenerDecimal(hijos.get(i).getEvaluacion());
        }
        String dato = "";
        for (int i = 0; i < ruleta.length; i++) {
            dato += "|" + ruleta[i] + "|";
        }

        return ruleta;
    }

//================================= fin de seleccion Ruleta ===================================
//====================================== Aleatorio ===================================
    private void aleatorizacion() {

        Collections.shuffle(nuevaGeneracion);

    }
}
