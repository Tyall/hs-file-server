package server;

import common.Response;

import java.io.DataOutputStream;
import java.io.IOException;

public class ServerResponseHandler {

    private final DataOutputStream output;

    public ServerResponseHandler(DataOutputStream output) {
        this.output = output;
    }

    public void sendResponse(Response response) {
        try {
            output.writeUTF(response.toString());
            writeFileIfPresent(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFileIfPresent(Response response) throws IOException {
        if (response.getFileContent() != null && response.getFileContent().length > 0) {
            output.writeInt(response.getFileContent().length);
            output.write(response.getFileContent());
        }
    }
}
