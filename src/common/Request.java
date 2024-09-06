package common;

import java.io.DataInputStream;
import java.io.EOFException;

public class Request {

    private final CustomHttpRequest method;
    private final FileIdentificationMethod fileIdentificationMethod;
    private final String identifier;
    private final String localFileName;
    private final String remoteFileName;
    private final byte[] fileContent;

    public CustomHttpRequest getMethod() {
        return method;
    }

    public FileIdentificationMethod getFileIdentificationMethod() {
        return fileIdentificationMethod;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLocalFileName() {
        return localFileName;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    Request(RequestBuilder builder) {
        this.method = builder.type;
        this.fileIdentificationMethod = builder.fileIdentificationMethod;
        this.identifier = builder.identifier;
        this.localFileName = builder.localFileName;
        this.remoteFileName = builder.remoteFileName;
        this.fileContent = builder.fileContent;
    }

    @Override
    public String toString() {
        String stringRequest = "";
        if (this.method == CustomHttpRequest.EXIT) {
            stringRequest = "EXIT";
        } else if (this.method == CustomHttpRequest.PUT) {
            stringRequest = String.format("%s %s ",
                    this.method.getMethod(),
                    this.remoteFileName);
        } else {
            stringRequest = String.format("%s %s %s ",
                    this.method.getMethod(),
                    this.fileIdentificationMethod.getMethod(),
                    this.identifier);
        }
        return stringRequest;
    }

    public static class RequestBuilder {

        private CustomHttpRequest type;
        private FileIdentificationMethod fileIdentificationMethod;
        private String identifier;
        private String localFileName;
        private String remoteFileName;
        private byte[] fileContent;

        public RequestBuilder() {}

        public RequestBuilder(CustomHttpRequest type) {
            this.type = type;
        }

        public RequestBuilder identifiedBy(FileIdentificationMethod fileIdentificationMethod) {
            this.fileIdentificationMethod = fileIdentificationMethod;
            return this;
        }

        public RequestBuilder setIdentifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public RequestBuilder setLocalFileName(String localFileName) {
            this.localFileName = localFileName;
            return this;
        }

        public RequestBuilder setRemoteFileName(String remoteFileName) {
            this.remoteFileName = remoteFileName;
            return this;
        }

        public RequestBuilder setFileContent(DataInputStream input) {

            if (this.type != CustomHttpRequest.PUT) {
                return this;
            }

            byte[] fileBytes = new byte[0];
            try {
                int bytesLength = input.readInt();
                fileBytes = new byte[bytesLength];
                input.readFully(fileBytes, 0, fileBytes.length);
                this.fileContent = fileBytes;
            } catch (EOFException e) {
                this.fileContent = new byte[0];
            } catch (Exception e) {
                e.printStackTrace();
            }

            return this;
        }

        public RequestBuilder fromRequestString(String requestString) {
            String[] requestParams = requestString.split(" ");
            if (requestParams.length < 1) {
                return this;
            }
            this.type = CustomHttpRequest.valueOf(requestParams[0]);
            if (type == CustomHttpRequest.EXIT || requestParams.length < 2) {
                return this;
            } else if (type == CustomHttpRequest.PUT) {
                this.remoteFileName = requestParams[1];
            } else if (requestParams.length > 2) {
                this.fileIdentificationMethod = FileIdentificationMethod.valueOf(requestParams[1]);
                this.identifier = requestParams[2];
            }
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }

}
