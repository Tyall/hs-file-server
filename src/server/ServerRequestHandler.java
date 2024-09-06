package server;

import common.Request;
import common.Response;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerRequestHandler {

    private final DataInputStream input;
    private final FileServices fileServices;

    public ServerRequestHandler(DataInputStream input, FileTracker fileTracker) {
        this.input = input;
        this.fileServices = new FileServices(fileTracker);
    }

    public Request receiveRequest() throws IOException {
        Request request = null;

        try {
            String requestString = input.readUTF();

            request = new Request.RequestBuilder()
                    .fromRequestString(requestString)
                    .setFileContent(input)
                    .build();
            System.out.println("Received request " + requestString);
        } catch (Exception e) {
            System.out.println("Error while retrieving the request " + e);
        }
        return request;
    }

    public Response processRequest(Request request) {
        return switch (request.getMethod()) {
            case GET -> fileServices.handleGetRequest(request);
            case PUT -> fileServices.handlePutRequest(request);
            case DELETE -> fileServices.handleDeleteRequest(request);
            case EXIT -> null;
        };
    }

}
