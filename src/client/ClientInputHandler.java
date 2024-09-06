package client;

import common.CustomHttpRequest;
import common.FileIdentificationMethod;
import common.Request;

import java.util.Scanner;

import static client.Messages.ENTER_ACTION_MESSAGE;
import static client.UserInputUtils.*;

public class ClientInputHandler {

    private final Scanner scanner;

    public ClientInputHandler(Scanner scanner) {
        this.scanner = scanner;
    }

    public Request getRequestFromInput() {
        String userAction = getLineByScanner(scanner, ENTER_ACTION_MESSAGE);

        return switch (userAction) {
            case "exit" -> handleExitAction();
            case "1" -> handleGetAction();
            case "2" -> handlePutAction();
            case "3" -> handleDeleteAction();
            default -> throw new IllegalStateException("Entered user action invalid: " + userAction);
        };
    }

    private Request handleGetAction() {
        FileIdentificationMethod identificationMethod = getIdentificationMethod(scanner, CustomHttpRequest.GET.getMethod().toLowerCase());

        return new Request.RequestBuilder(CustomHttpRequest.GET)
                .identifiedBy(identificationMethod)
                .setIdentifier(getIdentifier(scanner, identificationMethod))
                .build();
    }

    private Request handlePutAction() {
        return new Request.RequestBuilder(CustomHttpRequest.PUT)
                .setIdentifier(getLocalFilename(scanner))
                .setRemoteFileName(getRemoteFilename(scanner))
                .build();
    }

    private Request handleDeleteAction() {
        FileIdentificationMethod identificationMethod = getIdentificationMethod(scanner,  CustomHttpRequest.DELETE.getMethod().toLowerCase());

        return new Request.RequestBuilder(CustomHttpRequest.DELETE)
                .identifiedBy(identificationMethod)
                .setIdentifier(getIdentifier(scanner, identificationMethod))
                .build();
    }

    private Request handleExitAction() {
        return new Request.RequestBuilder(CustomHttpRequest.EXIT).build();
    }
}
