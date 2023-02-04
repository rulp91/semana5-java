package es.master.isia.controller;

import com.google.common.eventbus.Subscribe;
import com.google.common.io.Resources;
import es.master.isia.model.Event.CloseDirChooserEvent;
import es.master.isia.view.DirChooserView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;


/**
 * Controlador de la vista principal
 */
public class MainViewController {
    private static MainViewController instance;
    private final JFrame frame = new JFrame("Java");
    public static MainViewController getInstance() {
        if (instance == null) {
            instance = new MainViewController();
        }
        return instance;
    }

    /**
     * Muestra un JFrame de selección de directorio
     */
    public void ShowDirChooser(){

        DirChooserView panel = new DirChooserView();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );

        URL iconResource  = Resources.getResource("es.master.isia/img.png");
        frame.setIconImage(new ImageIcon(iconResource.getPath()).getImage());
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }

    /**
     * Recepciona el evento de final de procesamiento del directorio
     * y cierra JFrame
     * @param event
     */
    @Subscribe
    public void onEvent(CloseDirChooserEvent event) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
