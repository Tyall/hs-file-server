package client;

import common.CustomHttpRequest;
import common.Request;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static common.Configuration.CLIENT_FILE_DIRECTORY;

public class ClientRequestHandler {


    private final DataOutputStream output;

    public ClientRequestHandler(DataOutputStream output) {
        this.output = output;
    }

    public void sendRequest(Request request) throws InterruptedException {
        try {
            output.writeUTF(request.toString());
            writeFileIfPresent(request);
            Thread.sleep(1000);
            System.out.println("The request was sent.");
        } catch (InterruptedException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFileIfPresent(Request request) throws FileNotFoundException {
        if (CustomHttpRequest.PUT == request.getMethod()) {
            File file = new File(CLIENT_FILE_DIRECTORY + request.getIdentifier());
            if (file.exists()) {
                byte[] fileBytes = new byte[(int) file.length()];
                try (FileInputStream inputStream = new FileInputStream(file)) {
                    int readStatus = inputStream.read(fileBytes);
                    if (readStatus > 0) {
                        output.writeInt(fileBytes.length);
                        output.write(fileBytes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new FileNotFoundException("File " + request.getLocalFileName() + " was not found in predefined client file directory");
            }
        }
    }
}
