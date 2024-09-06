package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileServer {

    public static void run(String address, int port) {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        FileTracker tracker = FileTrackerServices.load();

        startServerAndHandleRequests(address, port, executor, tracker);
    }

    private static void startServerAndHandleRequests(String address, int port, ExecutorService executor, FileTracker tracker) {
        try (ServerSocket server = new ServerSocket(port, 500, InetAddress.getByName(address))) {

            System.out.println("Server started!");

            while (!server.isClosed()) {

                try {
                    Socket socket = server.accept();
                    executor.submit(() -> new FileServerThread(server, socket, tracker).run());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileTrackerServices.save(tracker);

            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private FileServer() {
        throw new IllegalStateException("Class not instantiable");
    }
}
