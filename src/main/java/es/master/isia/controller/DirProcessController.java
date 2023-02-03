package es.master.isia.controller;


import com.google.common.eventbus.Subscribe;
import es.master.isia.model.Event.SelectedDirEvent;

import java.io.File;
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
                    System.out.println(currentFile.getName());
            }
        }

        if(isTextFile(file))
            System.out.println(file.getName());

    }

    private boolean isTextFile(File file) throws IOException {
        String mimeType = Files.probeContentType(file.getAbsoluteFile().toPath());
        return  "text/plain".equalsIgnoreCase(mimeType);
    }
}
