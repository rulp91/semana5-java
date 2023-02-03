package es.master.isia.controller;


import com.google.common.eventbus.Subscribe;
import es.master.isia.model.Event.SelectedDirEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.EventListener;

public class DirProcessController  implements EventListener {

    private static DirProcessController instance;
    public static DirProcessController getInstance() {
        if (instance == null) {
            instance = new DirProcessController();
        }
        return instance;
    }

    @Subscribe
    public void onEvent(SelectedDirEvent event) {
        try {
            loopIterativelyDir(event.getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loopIterativelyDir(File file) throws IOException {

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for ( File currentFile: files) {
                if(currentFile.isDirectory())
                    loopIterativelyDir(currentFile);

                if(isTextFile(currentFile))
                    processFile(currentFile);

            }
        }

        if(isTextFile(file))
            processFile(file);

    }

    private void processFile(File file) throws IOException {

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

    private boolean isTextFile(File file) throws IOException {
        String mimeType = Files.probeContentType(file.getAbsoluteFile().toPath());
        return  "text/plain".equalsIgnoreCase(mimeType);
    }
}
