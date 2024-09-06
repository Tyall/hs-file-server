package server;

import java.io.*;

public class FileTrackerServices {

    private static final String FILE_TRACKER_PATH = System.getProperty("user.dir") + "src\\server\\data\\file_tracker";

    public static void save(FileTracker tracker) {
        File file = new File(FILE_TRACKER_PATH);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(tracker);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileTracker load() {
        File file = new File(FILE_TRACKER_PATH);
        FileTracker result = null;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            result = (FileTracker) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            result = new FileTracker();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private FileTrackerServices() {
        throw new IllegalStateException("Class not instantiable");
    }

}
