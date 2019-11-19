package clases;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Archivo {

    String informacion;
    String estructura;
    String ruta;

    public Archivo(String informacion, String estructura, String ruta) {

        this.informacion = informacion;
        this.estructura = estructura;
        this.ruta = ruta;
        ;
    }

    public void LimpiarTxt() {
        FileWriter fw = null;
        try {
            fw = new FileWriter(ruta);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write("");
            bf.close();
        } catch (IOException ex) {
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                }
            }
        }

    }

    public void guardarTxt() {
        FileWriter fw = null;
        try {
            fw = new FileWriter(ruta, true);
            BufferedWriter bf = new BufferedWriter(fw);
            bf.write(this.estructura);
            bf.write(this.informacion);
            bf.close();
           
        } catch (IOException ex) {
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public void Execute() {
        Runtime runTime = Runtime.getRuntime();
        try {
            Process proceso = runTime.exec("Explorer.exe " + "src\\Consolidado");
        } catch (IOException ex) {

        }
    }
}
