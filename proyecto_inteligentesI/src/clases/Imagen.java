package clases;

import GUI.panel_grafo;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Imagen {

    panel_grafo panel;
    String nombre;
    String ruta;

    public Imagen(panel_grafo panel, String nombre, String ruta) {
        this.panel = panel;
        this.nombre = nombre;
        this.ruta = ruta;
    }

    private void CrearCarpeta() {
        File Directorio = new File(ruta);
        if (!Directorio.exists()) {
            Directorio.mkdirs();
        }
    }

    public void dibujarImagen(Grafo grafo) {
        String formato = "png";
        this.CrearCarpeta();
        File fichero = new File(ruta + "\\" + nombre + "." + formato);
        BufferedImage imagen = new BufferedImage(panel.ancho, panel.alto, BufferedImage.TYPE_INT_RGB);

        Graphics g = imagen.getGraphics();

        g.setColor(panel.colorFondo);
        g.fillRect(0, 0, panel.ancho, panel.alto);
        panel.dibujar(g, grafo);

        try {
            ImageIO.write(imagen, formato, fichero);
        } catch (IOException e) {
        }

    }
}
