package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class panel_informacion extends JPanel  {
    
    public int ancho, alto, x, y;
  
    
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
    
    public void adicionarLabel(String texto, Rectangle r) {
        JLabel btn_solucionar = new JLabel(texto);
        configurarLabel(btn_solucionar, r);
    }
    
    private void configurarLabel(JLabel label, Rectangle r) {
        label.setFont(new Font("Arial", 1, 15));
        label.setForeground(Color.white);
        label.setBounds(r);
        label.setVisible(true);
        add(label);
        repaint();
    }
    
    
}
