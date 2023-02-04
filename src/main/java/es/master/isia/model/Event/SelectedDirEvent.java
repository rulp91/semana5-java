package es.master.isia.model.Event;

import java.io.File;

/**
 * Evento de selección de directorio
 */
public class SelectedDirEvent {
    private final File file;
    private final String searchString;

    public SelectedDirEvent(File file, String searchString) {
        this.file = file;
        this.searchString = searchString;
    }
    public File getFile() {
        return file;
    }

    public String getSearchString() {
        return searchString;
    }
}
