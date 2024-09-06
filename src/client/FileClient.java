package client;

import common.CustomHttpRequest;
import common.Request;
import common.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class FileClient {

    public static void run(String address, int port) {

        pauseClient();

        try (Socket socket = new Socket(InetAddress.getByName(address), port);
             Scanner scanner = new Scanner(System.in);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            ClientInputHandler inputHandler = new ClientInputHandler(scanner);
            ClientRequestHandler requestHandler = new ClientRequestHandler(output);
            ClientResponseHandler responseHandler = new ClientResponseHandler(input, scanner);

            Request request = inputHandler.getRequestFromInput();

            requestHandler.sendRequest(request);

            if (request.getMethod() == CustomHttpRequest.EXIT) {
                return;
            }
            Response response = responseHandler.receiveResponse();

            responseHandler.processResponse(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void pauseClient() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private FileClient() {
        throw new IllegalStateException("Class not instantiable");
    }
}
