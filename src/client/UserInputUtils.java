package client;

import common.FileIdentificationMethod;

import java.util.Scanner;

import static client.Messages.*;

public class UserInputUtils {

    public static String getLocalFilename(Scanner scanner) {
        return getLineByScanner(scanner, ENTER_LOCAL_FILENAME);
    }

    public static String getFileToSaveName(Scanner scanner) {
        return getLineByScanner(scanner, RESPONSE_MESSAGE_200_GET);
    }

    public static String getRemoteFilename(Scanner scanner) {
        return getLineByScanner(scanner, ENTER_REMOTE_FILENAME);
    }

    public static String getIdentifier(Scanner scanner, FileIdentificationMethod identificationMethod) {
        String message = FileIdentificationMethod.BY_NAME.equals(identificationMethod) ? ENTER_FILENAME_MESSAGE :
                FileIdentificationMethod.BY_ID.equals(identificationMethod) ? ENTER_FILE_ID_MESSAGE : ERROR;
        return getLineByScanner(scanner, message);
    }

    public static FileIdentificationMethod getIdentificationMethod(Scanner scanner, String action) {
        String identificationMethodNumber = getLineByScannerPromptWithParam(scanner, ENTER_METHOD_MESSAGE, action);
        return getFileIdentificationMethodFromNumber(identificationMethodNumber);
    }

    public static String getLineByScanner(Scanner scanner, String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    public static String getLineByScannerPromptWithParam(Scanner scanner, String prompt, String param) {
        System.out.printf(prompt, param);
        return scanner.nextLine();
    }

    private static FileIdentificationMethod getFileIdentificationMethodFromNumber(String identificationMethodNumber) {
        return switch (identificationMethodNumber) {
            case "1" -> FileIdentificationMethod.BY_NAME;
            case "2" -> FileIdentificationMethod.BY_ID;
            default -> throw new IllegalStateException("Unexpected identification method number: " + identificationMethodNumber);
        };
    }

    private UserInputUtils() {
        throw new IllegalStateException("Class not instantiable");
    }

}
