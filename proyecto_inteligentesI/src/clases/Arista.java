package clases;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Arista {

    public Nodo origen;
    public Nodo destino;
    public int distancia;

    public Arista(Nodo origen, Nodo destino, int distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    
    public void dibujar(Graphics g) {
        // JOptionPane.showMessageDialog(null, +origen.ubicacion.x + " " + +origen.ubicacion.y);
        int x1 = origen.rectangulo.x;
        int y1 = origen.rectangulo.y;
        int x2 = destino.rectangulo.x;
        int y2 = destino.rectangulo.y;
        int alto = origen.rectangulo.height;
        int ancho = origen.rectangulo.width;

        g.drawLine(x1 + ancho / 2, y1 + alto / 2, x2 + ancho / 2, y2 + alto / 2);

        g.setFont(new Font("Arial Black", 0, 15));
        g.drawString(String.valueOf(distancia), (int) ((x1 + x2 + ancho / 2) / 2), (int) ((y1 + y2 + alto / 2) / 2));

    }
}
