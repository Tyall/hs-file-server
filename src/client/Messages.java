package client;

public interface Messages {

    String ENTER_ACTION_MESSAGE = "Enter action (1 - get a file, 2 - create a file, 3 - delete a file):";
    String ENTER_METHOD_MESSAGE = "Do you want to %s the file by name or by id (1 - name, 2 - id):";

    String ENTER_FILE_ID_MESSAGE = "Enter id:";
    String ENTER_FILENAME_MESSAGE = "Enter filename:";

    String ENTER_LOCAL_FILENAME = "Enter name of the file:";
    String ENTER_REMOTE_FILENAME = "Enter name of the file to be saved on server:";

    String RESPONSE_MESSAGE_200_GET = "The file was downloaded! Specify a name for it:";
    String RESPONSE_MESSAGE_200_PUT = "Response says that file is saved! ID = %d%n";
    String RESPONSE_MESSAGE_200_DELETE = "The response says that this file was deleted successfully!";

    String RESPONSE_MESSAGE_403 = "The response says that creating the file was forbidden!";
    String RESPONSE_MESSAGE_404 = "The response says that this file is not found!";

    String ERROR = "ERROR";
}
