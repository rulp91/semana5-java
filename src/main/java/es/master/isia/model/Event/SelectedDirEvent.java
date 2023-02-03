package es.master.isia.model.Event;

import java.io.File;

public class SelectedDirEvent {
    private File file;

    public SelectedDirEvent(File message) {
        this.file = message;
    }
    public File getFile() {
        return file;
    }
}
