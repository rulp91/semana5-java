package es.master.isia.view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.google.common.eventbus.EventBus;
import es.master.isia.controller.DirProcessController;
import es.master.isia.model.Event.SelectedDirEvent;
import org.springframework.beans.factory.annotation.Autowired;

public class DirChooserView extends JPanel implements ActionListener, DocumentListener {

    @Autowired
    private final EventBus eventbus = new EventBus();

    private  JTextField tf= new JTextField();
    private JLabel l = new JLabel();

    public DirChooserView() {

        initView();
        eventbus.register(DirProcessController.getInstance());
    }

    /**
     * Inicializa la vista
     */
    private void initView() {
        this.setBackground(new Color(233, 241, 164));
        this.setBorder(BorderFactory.createTitledBorder("<html>Selecci&oacute;n de directorio con Java<html>"));
        l.setText("<html>Introduzca la cadena de b&uacute;squeda y a continuaci&oacute;seleccione el directorio<html>");
        l.setBounds(10,20, 430,40);
        tf.setText("wax synthase");
        tf.setBounds(10,60, 410,20);
        tf.getDocument().addDocumentListener(this);
        JButton go = new JButton("Seleccione el directorio");
        go.setBounds(220,100,200,30);
        go.addActionListener(this);
        add(tf);
        add(l);
        add(go);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Seleccione el directorio a analizar");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int selectedOption = chooser.showOpenDialog(this);
        String searchString = tf.getText().trim();
        if (selectedOption == JFileChooser.APPROVE_OPTION && !searchString.isEmpty())
            eventbus.post(new SelectedDirEvent(chooser.getSelectedFile(), searchString));
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(500, 200);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        showWarningIfNeedle();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        showWarningIfNeedle();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        showWarningIfNeedle();
    }

    /**
     * Muestra una advertencia en caso de que sea necesario
     */
    private void showWarningIfNeedle() {
        if( tf.getText().trim().isEmpty())
            l.setText("<html>Introduzca la <strong><font color='red'>cadena de b&uacute;squeda</font><strong> y a continuaci&oacute;seleccione el directorio<html>");
        else
            l.setText("<html>Introduzca la cadena de b&uacute;squeda y a continuaci&oacute;seleccione el directorio<html>");

        this.setLayout(null);
    }
}
