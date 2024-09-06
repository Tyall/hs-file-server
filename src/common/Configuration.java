package common;

public interface Configuration {

    String SERVER_ADDRESS = "127.0.0.1";
    int SERVER_PORT = 12345;

    String SERVER_FILE_DIRECTORY = System.getProperty("user.dir") + "\\src\\server\\data\\";

    String CLIENT_FILE_DIRECTORY = System.getProperty("user.dir") + "\\src\\client\\data\\";

    String SEPARATOR = " ";
}
