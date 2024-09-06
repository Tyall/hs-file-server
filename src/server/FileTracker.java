package server;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileTracker implements Serializable {

    private volatile Map<Integer, String> files;

    public FileTracker() {
        this.files = new ConcurrentHashMap<>();
    }

    public Map<Integer, String> getFiles() {
        return files;
    }

    public void setFiles(Map<Integer, String> files) {
        this.files = files;
    }

}
