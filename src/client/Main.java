package client;

import static common.Configuration.SERVER_ADDRESS;
import static common.Configuration.SERVER_PORT;

public class Main {

    public static void main(String[] args) {
        FileClient.run(SERVER_ADDRESS, SERVER_PORT);
    }
}
