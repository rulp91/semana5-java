package es.master.isia.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import com.google.common.eventbus.EventBus;
import es.master.isia.controller.DirProcessController;
import es.master.isia.model.Event.SelectedDirEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class DirChooserView extends JPanel implements ActionListener {

    private JButton go;

    private JFileChooser chooser;
    private String choosertitle;

    @Autowired
    private EventBus eventbus = new EventBus();

    public DirChooserView() {
        go = new JButton("Seleccione el directorio");
        choosertitle = "Seleccione el directorio a analizar";
        go.addActionListener(this);
        add(go);
        eventbus.register(DirProcessController.getInstance());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            eventbus.post(new SelectedDirEvent(chooser.getSelectedFile()));
        else
            System.out.println("No ha seleccionado nada ");
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(400, 200);
    }


}
