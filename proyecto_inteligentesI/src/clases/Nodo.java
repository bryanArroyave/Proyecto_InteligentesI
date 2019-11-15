package clases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Nodo {

    public Rectangle rectangulo;
    public String Nombre;
    private Color color;
    public String binario;

    public Nodo(Rectangle rectangulo, String Nombre) {
        this.rectangulo = rectangulo;
        this.Nombre = Nombre;
        int R = (int) (Math.random() * 255);
        int G = (int) (Math.random() * 255);
        int B = (int) (Math.random() * 255);
        binario = "";
        color = new Color(R, G, B);

    }

    public void cambiarBinario(int cantidad) {
        binario = Integer.toBinaryString(Integer.parseInt(Nombre.substring(1, Nombre.length())));
        String ceros = "";
        for (int i = 0; i < (cantidad - binario.trim().length()); i++) {
            ceros += "0";

        }
        binario = ceros + binario;

       
    }

    public boolean comprobarUbicacion(Rectangle c) {
        return !c.intersects(rectangulo);
    }

    public void dibujar(Graphics g) {
        g.setFont(new Font("Arial Black", 1, 15));
        // g.drawImage(imagen.getImage(), rectangulo.x, rectangulo.y, null);
        g.setColor(color);
        g.fillOval(rectangulo.x, rectangulo.y, rectangulo.width, rectangulo.height);
        g.setColor(Color.black);
        g.drawString(Nombre, -7 + (rectangulo.x) + rectangulo.width / 2, (rectangulo.y) + rectangulo.height / 2 + 3);
    }
}
