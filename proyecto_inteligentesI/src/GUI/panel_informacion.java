package GUI;

import java.awt.Color;
import javax.swing.JPanel;

public class panel_informacion extends JPanel {

    private int ancho, alto, x, y;

    public panel_informacion(int ancho, int alto, int x, int y) {
        this.ancho = ancho;
        this.alto = alto;
        this.x = x;
        this.y = y;
        
        configurarPanel();
    }

    private void configurarPanel() {
        this.setSize(ancho, alto);
        this.setLocation(x, y);
        this.setVisible(true);
        this.setLayout(null);
        this.setBackground(new Color(8, 77, 110));
    }
}
