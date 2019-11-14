package GUI;


import java.awt.Toolkit;
import javax.swing.JFrame;

public class Main extends JFrame {

    private final int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int alto = Toolkit.getDefaultToolkit().getScreenSize().height;

    panel_principal principal;

    public Main() {
        super("Interfaz Inicial");
        configurarFrame();
        inicializarComponentes();
        agregarComponentes();
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
    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }
//=========================================================================
}
