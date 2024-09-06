package server;

import common.CustomHttpRequest;
import common.Request;
import common.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServerThread implements Runnable {

    private final ServerSocket server;
    private final Socket socket;
    private final FileTracker tracker;

    public FileServerThread(ServerSocket server, Socket socket, FileTracker tracker) {
        this.server = server;
        this.socket = socket;
        this.tracker = tracker;
    }

    @Override
    public void run() {
        try (DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            ServerRequestHandler requestHandler = new ServerRequestHandler(input, tracker);
            ServerResponseHandler responseHandler = new ServerResponseHandler(output);

            Request request = requestHandler.receiveRequest();

            if (CustomHttpRequest.EXIT == request.getMethod()) {
                socket.close();
                server.close();
                return;
            }

            Response response = requestHandler.processRequest(request);

            responseHandler.sendResponse(response);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
