package clases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Nodo {

    public Rectangle rectangulo;
    public String Nombre;
    private final Color color;
    public String binario;

    public Nodo(Rectangle rectangulo, String Nombre) {
        this.rectangulo = rectangulo;
        this.Nombre = Nombre;
        int R = (int) ((Math.random() * 225) + 30);
        int G = (int) ((Math.random() * 225) + 30);
        int B = (int) ((Math.random() * 225) + 30);
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
        g.setColor(color);
        g.fillOval(rectangulo.x, rectangulo.y, rectangulo.width, rectangulo.height);
        g.setColor(Color.white);
        g.drawString(Nombre, -7 + (rectangulo.x) + rectangulo.width / 2, (rectangulo.y) + rectangulo.height / 2 + 3);
    }
}
