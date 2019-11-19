package clases;

import static GUI.panel_grafo.grafo;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Algoritmos implements Runnable {

    //==================================== CONSTANTES =====================================
    private final int CANTPOBLACION = 50;
    private final int MAXBITS;
    private final int CANTBITS;
    private final float PORCENTAJEMUTACION = 0.3f;
    public final int CANTIDADITERACIONES = 100000;

    private final float ALPHA = 2;
    //==================================== VARIABLES =======================================
    public float porcentaje;
    private String[] padres;
    private String[] nuevaGeneracion;
    public boolean termino;
    public String rutaActual;
    private final int[] costos;
    private final float[] evaluacion;
    private final float[] aptitud;
    private final int[] infactibilidad;
    private final int[] nodosVisitados;
    private final int[] hijos;
    public String informacion;
    public String cabecera;
    public int itecacionActual = 0;

    public Algoritmos() {
        this.MAXBITS = grafo.calcularMaximoBits();
        this.CANTBITS = grafo.cant_bits;
        this.costos = new int[CANTPOBLACION];
        this.hijos = new int[CANTPOBLACION];
        this.nodosVisitados = new int[CANTPOBLACION];
        this.infactibilidad = new int[CANTPOBLACION];
        this.evaluacion = new float[CANTPOBLACION];
        this.aptitud = new float[CANTPOBLACION];
        this.padres = new String[CANTPOBLACION];
        this.nuevaGeneracion = new String[CANTPOBLACION];
        this.porcentaje = 0;
        this.termino = false;
        this.rutaActual = "";
        this.informacion = "";
    }

    public void empezarGenetico() {
        generarHijos();

        while (itecacionActual != CANTIDADITERACIONES) {
            for (int i = 0; i < CANTPOBLACION; i++) {
                this.nodosVisitados[i] = 0;
            }
            seleccionar();
            modificarRuta();
            this.informacion = "";
            for (int i = 0; i < CANTPOBLACION; i++) {
                DecimalFormat f = new DecimalFormat("#.000");
                this.informacion += (this.padres[i] + "\t" + this.costos[i]
                        + "\t" + this.infactibilidad[i] + "\t" + this.nodosVisitados[i]
                        + "\t" + f.format(this.evaluacion[i]) + "\t" + f.format(this.aptitud[i]) + "\t" + this.hijos[i] + "\n");
            }

            cruzar();
            if (terminar()) {
                break;
            }
            mutar();

            this.padres = Arrays.copyOf(nuevaGeneracion, CANTPOBLACION);

        }
        termino = true;

    }

    private void modificarRuta() {
        String cromosoma = "";
        for (int i = 0; i < CANTPOBLACION; i += MAXBITS) {
            String ciudad = "";
            for (int j = 0; j < MAXBITS / CANTBITS; j++) {
                String gen = "";
                for (int k = j * CANTBITS; k < j * CANTBITS + CANTBITS; k++) {
                    gen += nuevaGeneracion[i].charAt(k);
                }
                try {
                    ciudad += grafo.buscarNodoBinario(gen).Nombre + ",";
                } catch (Exception e) {
                }

            }
            rutaActual = ciudad;

        }
    }

    private float porcentajeCoincidencias() {

        int total = 0;
        char[] datos = nuevaGeneracion[0].toCharArray();
        boolean[] estados = new boolean[MAXBITS];

        for (int i = 0; i < MAXBITS; i++) {
            boolean estado = true;
            for (int j = 0; j < CANTPOBLACION; j++) {
                estado = estado && (this.nuevaGeneracion[j].charAt(i) == datos[i]);
            }
            estados[i] = estado;
            if (estado) {
                total++;
            }
        }

        return (float) total * 100 / MAXBITS;
    }

    private boolean terminar() {

        porcentaje = porcentajeCoincidencias();
        if (porcentaje >= 98) {

            termino = true;

            return true;
        }
        itecacionActual++;
        return false;
    }

    //============================== GENERAR HIJOS ================================
    private void generarHijos() {
        grafo.calcularCantidadBits();

        for (int i = 0; i < CANTPOBLACION; i++) {
            String cromosoma = "";
            for (int j = 0; j < MAXBITS; j++) {
                cromosoma += (int) (Math.round(Math.random()));
            }

            this.padres[i] = cromosoma;

        }

    }

    public void calcularCabecera() {
        int cantTabs = (int) MAXBITS / 7;
        this.cabecera = "===============================================================================================\n";
        this.cabecera += "Cromosoma";
        for (int i = 0; i < cantTabs; i++) {
            this.cabecera += "\t";
        }
        this.cabecera += "Costo\tInfact\tVis\tEval\tAptitud\tC-Hijos\n";
        this.cabecera += "===============================================================================================\n";
        System.out.println(this.cabecera);
        System.out.println(this.informacion);

    }

    private float promedioEvaluaciones() {
        float promedio = 0;

        for (int i = 0; i < CANTPOBLACION; i++) {
            promedio += (this.evaluacion[i]);
        }
        return (float) promedio / CANTPOBLACION;
    }

    private void calcularAptitud() {
        float promedio = this.promedioEvaluaciones();

        for (int i = 0; i < CANTPOBLACION; i++) {
            this.aptitud[i] = (this.evaluacion[i] / promedio);
            this.hijos[i] = (int) this.aptitud[i];
        }
    }

    private void ruleta() {

        int hijosRestantes = 0;

        for (int i = 0; i < CANTPOBLACION; i++) {
            hijosRestantes += this.hijos[i];
        }

        for (int i = 0; i < CANTPOBLACION - hijosRestantes; i++) {
            int pos = escogerHijoRuleta(llenarRuleta());
            this.hijos[pos]++;

        }
    }

    private float[] llenarRuleta() {
        float ruleta[] = new float[CANTPOBLACION];

        ruleta[0] = this.evaluacion[0] - this.hijos[0];

        for (int i = 1; i < ruleta.length; i++) {
            ruleta[i] = ruleta[i - 1] + this.aptitud[i] - this.hijos[i];

        }
        String dato = "";
        for (int i = 0; i < ruleta.length; i++) {
            dato += "|" + ruleta[i] + "|";
        }

        return ruleta;
    }

    private int escogerHijoRuleta(float[] ruleta) {

        int posicion = 0;
        float aleatorio = (float) (Math.random() * ruleta[ruleta.length - 1]);
        for (int i = 0; i < ruleta.length - 1; i++) {
            if (aleatorio > ruleta[i + 1]) {
                posicion = i + 1;
            }
        }

        return posicion;
    }

    private void calcularCostos() {
        LinkedList<String> visitados = new LinkedList<>();
        String[] ruta = new String[MAXBITS / CANTBITS];

        for (int i = 0; i < CANTPOBLACION; i++) {
            this.costos[i] = 0;
            this.infactibilidad[i] = 0;

            for (int j = 0; j < (MAXBITS / CANTBITS); j++) {
                ruta[j] = this.padres[i].substring(j * CANTBITS, j * CANTBITS + CANTBITS);
            }
            this.costos[i] = calcularCosto(ruta, i);

        }
    }

    private int calcularCosto(String[] ruta, int pos) {
        String o = "", d = "";
        int peso = 0;
        LinkedList<String> visitados = new LinkedList<>();

        for (int i = 0; i < ruta.length; i++) {

            if (!visitados.contains(ruta[i])) {
                visitados.add(ruta[i]);
                this.nodosVisitados[pos]++;

            } else {
                this.infactibilidad[pos]++;
            }
            if (i < ruta.length - 1) {
                try {
                    o = grafo.buscarNodoBinario(ruta[i]).Nombre;
                    d = grafo.buscarNodoBinario(ruta[i + 1]).Nombre;

                    if (!o.equals(d)) {

                        peso += grafo.buscarArista(o, d).distancia;

                    } else {
                        this.infactibilidad[pos]++;
                    }
                } catch (Exception e) {

                    this.infactibilidad[pos]++;
                }
            }
        }
        return peso;
    }

    private void calcularEvaluacion() {
        for (int i = 0; i < CANTPOBLACION; i++) {
            float pesoNormalizado = ((float) this.costos[i] / (float) Grafo.maxPeso);
            float visitadosNormalizado = ((float) this.nodosVisitados[i]) / (float) Grafo.maxCantNodos;
            float infactibilidadNormalizada = (float) ((float) this.infactibilidad[i] / (6 + Math.pow(2, CANTBITS)));

            this.evaluacion[i] = (float) (pesoNormalizado * 0.34 + visitadosNormalizado * 0.33 - infactibilidadNormalizada * 0.33);
        }
    }

    //=============================== SELECCION ===================================
    private void seleccionar() {
        calcularCostos();
        calcularEvaluacion();
        calcularAptitud();
        ruleta();
        crearNuevaGeneracion();

    }

    private void aleatorizacion() {
        LinkedList<String> ngen = new LinkedList<>();
        for (int j = 0; j < CANTPOBLACION; j++) {
            ngen.add(this.nuevaGeneracion[j]);
        }
        Collections.shuffle(ngen);

        for (int j = 0; j < CANTPOBLACION; j++) {
            this.nuevaGeneracion[j] = ngen.get(j);
        }
    }

    private void crearNuevaGeneracion() {
        int k = 0;
        for (int i = 0; i < CANTPOBLACION; i++) {
            for (int j = 0; j < this.hijos[i]; j++) {
                if (k < CANTPOBLACION) {
                    this.nuevaGeneracion[k] = this.padres[i];
                    k++;
                }

            }

        }
        aleatorizacion();
        aleatorizacion();
    }
    // ============================= CRUZAR =======================================

    private void cruzar() {

        String[] auxiliar = Arrays.copyOf(nuevaGeneracion, CANTPOBLACION);
        int random[] = new int[CANTPOBLACION];

        for (int i = 0; i < CANTPOBLACION; i += 2) {
            String c1 = "";
            String c2 = "";

            int posRandom = (int) (Math.random() * MAXBITS - 1) + 1;

            random[i] = posRandom;
            random[i + 1] = posRandom;

            String h1 = nuevaGeneracion[i];
            String h2 = nuevaGeneracion[i + 1];
            c1 = h1.substring(0, posRandom) + h2.substring(posRandom, h2.length());
            c2 = h2.substring(0, posRandom) + h1.substring(posRandom, h1.length());

            auxiliar[i] = c1;
            auxiliar[i + 1] = c2;

        }

        nuevaGeneracion = Arrays.copyOf(auxiliar, CANTPOBLACION);

    }
// ============================== MUTAR =======================================

    private void mutar() {
        for (int i = 0; i < CANTPOBLACION; i++) {

            double probabilidad = (Math.random());

            if (probabilidad <= PORCENTAJEMUTACION) {
                String nuevoCromosoma = nuevaGeneracion[i];
                int posAleatoria = (int) (Math.random() * MAXBITS);

                char gen = nuevoCromosoma.charAt(posAleatoria);

                int nuevoGen = 1 - Integer.parseInt(gen + "");

                nuevoCromosoma = reemplazarBit(nuevoCromosoma, posAleatoria, nuevoGen);
                nuevaGeneracion[i] = nuevoCromosoma;

            }

        }
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

    @Override
    public void run() {
        empezarGenetico();
    }
}
