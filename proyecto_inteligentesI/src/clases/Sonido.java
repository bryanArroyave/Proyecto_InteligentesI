package clases;

import java.applet.AudioClip;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sonido implements Runnable {

    AudioClip sonido;
    Thread t;

    public Sonido(String ruta) {
        sonido = java.applet.Applet.newAudioClip(getClass().getResource(ruta));
        t = new Thread(this);
        t.start();
    }

    public void play() {
        sonido.play();
    }

    public void stop() {
        sonido.stop();
    }

    @Override
    public void run() {
        play();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
           
        }

    }

}
