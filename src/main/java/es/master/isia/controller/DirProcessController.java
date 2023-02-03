package es.master.isia.controller;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import es.master.isia.model.Event.CloseDirChooserEvent;
import es.master.isia.model.Event.SelectedDirEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;


/**
 * Controlador singleton de procesado de directorios
 */
public class DirProcessController{

    @Autowired
    private final EventBus eventbus = new EventBus();

    private static DirProcessController instance;

    private DirProcessController() {
        eventbus.register(MainViewController.getInstance());
    }

    /**
     * Singleton del controlador
     * @return
     */
    public static DirProcessController getInstance() {
        if (instance == null)
            instance = new DirProcessController();

        return instance;
    }

    /**
     * Recepciona el evento de selección de un directorio
     * y lanza su procesamiento
     * @param event
     */
    @Subscribe
    public void onEvent(SelectedDirEvent event) {
        try {
            loopIterativelyDir(event.getFile());
            eventbus.post(new CloseDirChooserEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Recorre de forma iterativa el arbol de directorios y busca las coincidencias
     * @param file
     * @throws IOException
     */
    private void loopIterativelyDir(File file) throws IOException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for ( File currentFile: files) {
                if(currentFile.isDirectory())
                    loopIterativelyDir(currentFile);

                if(isTextFile(currentFile))
                    processTextFile(currentFile);

            }
        }

        if(isTextFile(file))
            processTextFile(file);

    }

    /**
     * Procesa la búsqueda de texto dentro del fichero
     * @param file
     * @throws IOException
     */
    private void processTextFile(File file) throws IOException {

        BufferedReader reader;

        reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        boolean dirIsPrinted = false;
        while (line != null) {
            if(line.contains("wax synthase")) {
                if(!dirIsPrinted) {
                    System.out.println(file.getAbsolutePath());
                    dirIsPrinted = true;
                }
                System.out.println("\t".concat(line));
            }
            line = reader.readLine();
        }

        reader.close();

    }

    /**
     * Retorna si el fichero es un fichero de texto
     * @param file
     * @return
     * @throws IOException
     */
    private boolean isTextFile(File file) throws IOException {
        String mimeType = Files.probeContentType(file.getAbsoluteFile().toPath());
        return  "text/plain".equalsIgnoreCase(mimeType);
    }
}
