package common;

import java.io.DataInputStream;
import java.io.EOFException;

import static common.Configuration.SEPARATOR;

public class Response {

    private final CustomHttpRequest requestType;
    private final CustomHttpResponse code;
    private final Integer identifier;
    private final byte[] fileContent;

    public CustomHttpRequest getRequestType() {
        return requestType;
    }

    public CustomHttpResponse getCode() {
        return code;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    Response(ResponseBuilder builder) {
        this.requestType = builder.requestType;;
        this.code = builder.code;
        this.identifier = builder.identifier;
        this.fileContent = builder.fileContent;
    }

    @Override
    public String toString() {
        StringBuilder stringResponse = new StringBuilder(this.code.getCode().toString());

        if (this.code == CustomHttpResponse.SUCCESS) {
            if (this.requestType == CustomHttpRequest.GET) {
                stringResponse.append(SEPARATOR);
            } else if (this.requestType == CustomHttpRequest.PUT) {
                stringResponse.append(SEPARATOR);
                stringResponse.append(this.identifier);
            }
        }

        return stringResponse.toString();
    }

    public static class ResponseBuilder {

        private CustomHttpRequest requestType;
        private CustomHttpResponse code;
        private Integer identifier;
        private byte[] fileContent;

        public ResponseBuilder() {
        }

        public ResponseBuilder(CustomHttpRequest requestType, CustomHttpResponse code) {
            this.requestType = requestType;
            this.code = code;
        }

        public ResponseBuilder setIdentifier(Integer identifier) {
            this.identifier = identifier;
            return this;
        }

        public ResponseBuilder setFileContent(byte[] bytes) {
            this.fileContent = bytes;
            return this;
        }

        public ResponseBuilder setFileContent(DataInputStream input) {

            if (this.code != CustomHttpResponse.SUCCESS && this.requestType != CustomHttpRequest.GET) {
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

        public ResponseBuilder fromResponseString(String requestString) {
            String[] requestParams = requestString.split(" ");
            if (requestParams.length < 1) {
                return this;
            }

            this.code = CustomHttpResponse.getByCode(Integer.valueOf(requestParams[0]));

            if (requestParams.length > 1) {
                this.identifier = Integer.valueOf(requestParams[1]);
            }

            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }

}
