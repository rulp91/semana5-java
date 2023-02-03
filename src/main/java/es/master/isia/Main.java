package es.master.isia;

import es.master.isia.controller.DirProcessController;
import es.master.isia.view.DirChooserView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("");
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



        System.out.println("Hello world!");
    }
}