package client;

import common.Response;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;

import static client.Messages.*;
import static client.UserInputUtils.getFileToSaveName;
import static common.Configuration.CLIENT_FILE_DIRECTORY;

public class ClientResponseHandler {

    private final DataInputStream input;
    private final Scanner scanner;

    public ClientResponseHandler(DataInputStream input, Scanner scanner) {
        this.input = input;
        this.scanner = scanner;
    }

    public Response receiveResponse() {
        Response response = null;
        try {
            String responseString = input.readUTF();

            response = new Response.ResponseBuilder()
                    .fromResponseString(responseString)
                    .setFileContent(input)
                    .build();
        } catch (Exception e) {
            System.out.println("Error while retrieving the response " + e);
        }
        return response;
    }

    public void processResponse(Response response) {

        if (response == null) {
            return;
        }

        switch (response.getCode()) {
            case SUCCESS -> handleSuccessResponse(response);
            case FORBIDDEN -> printMessage(RESPONSE_MESSAGE_403);
            case NOT_FOUND -> printMessage(RESPONSE_MESSAGE_404);
            case INTERNAL_SERVER_ERROR -> throw new RuntimeException("Unknown error encountered");
        }
    }

    private void handleSuccessResponse(Response response) {
        if (response.getIdentifier() != null && response.getIdentifier() != -1) {
            printMessage(RESPONSE_MESSAGE_200_PUT, response.getIdentifier());
        } else if (response.getFileContent() != null && response.getFileContent().length > 0) {
            saveNewFileLocally(response);
        } else {
            printMessage(RESPONSE_MESSAGE_200_DELETE);
        }
    }

    private void saveNewFileLocally(Response response) {
        try {
            String fileToSaveName = getFileToSaveName(scanner);
            File fileToSave = new File(CLIENT_FILE_DIRECTORY + fileToSaveName);
            writeContentToFile(response, fileToSave);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeContentToFile(Response response, File fileToSave) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileToSave)) {
            fileOutputStream.write(response.getFileContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printMessage(String message, Object param) {
        System.out.printf(message, param);
    }

}
