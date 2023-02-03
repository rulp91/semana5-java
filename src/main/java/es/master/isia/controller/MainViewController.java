package es.master.isia.controller;

import com.google.common.eventbus.Subscribe;
import es.master.isia.model.Event.CloseDirChooserEvent;
import es.master.isia.view.DirChooserView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainViewController {
    private static MainViewController instance;
    private final JFrame frame = new JFrame("");
    public static MainViewController getInstance() {
        if (instance == null) {
            instance = new MainViewController();
        }
        return instance;
    }

    public void ShowDirChooser(){

        DirChooserView panel = new DirChooserView();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel,"Center");
        frame.setSize(panel.getPreferredSize());
        frame.setVisible(true);
    }

    @Subscribe
    public void onEvent(CloseDirChooserEvent event) {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}
