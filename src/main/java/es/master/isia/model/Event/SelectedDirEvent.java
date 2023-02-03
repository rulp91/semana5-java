package es.master.isia.model.Event;

import java.io.File;

/**
 * Evento de selección de directorio
 */
public class SelectedDirEvent {
    private final File file;

    public SelectedDirEvent(File message) {
        this.file = message;
    }
    public File getFile() {
        return file;
    }
}
