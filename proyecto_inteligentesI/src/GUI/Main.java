package GUI;

import clases.Sonido;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main extends JFrame {

    private final int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int alto = Toolkit.getDefaultToolkit().getScreenSize().height;

    panel_principal principal;

    public Main() {
        super("Proyecto Geneticos");
        configurarFrame();
        inicializarComponentes();
        agregarComponentes();
        setIconImage(new ImageIcon(getClass().getResource("../Recursos/icono.jpg")).getImage());

    }

    private void configurarFrame() {
        this.setSize(ancho, alto);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

    }

    private void inicializarComponentes() {
        principal = new panel_principal(ancho, alto);

    }

    private void agregarComponentes() {
        add(principal);
    }

//=========================================================================
//=============================PRINCIPAL===================================
//=========================================================================
    public static void main(String[] args) throws MalformedURLException {
        Main main = new Main();
        main.setVisible(true);
        Sonido s = new Sonido("../Recursos/audio.wav");
        
    }
//=========================================================================
}
