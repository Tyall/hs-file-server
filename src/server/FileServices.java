package server;

import common.CustomHttpResponse;
import common.FileIdentificationMethod;
import common.Request;
import common.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import static common.Configuration.SERVER_FILE_DIRECTORY;

public class FileServices {

    private final FileTracker fileTracker;
    private final Random random;


    public FileServices(FileTracker fileTracker) {
        this.fileTracker = fileTracker;
        this.random = new Random();
    }

    public Response handlePutRequest(Request request) {
        Response response = null;

        String fileName = getRemoteFilenameCheckForNull(request);
        File file = getFile(FileIdentificationMethod.BY_NAME, fileName);
        if (file.exists()) {
            response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.FORBIDDEN).build();
        } else {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                Integer createNewFileResult = createNewFile(request, file, fileOutputStream);
                response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.SUCCESS)
                        .setIdentifier(createNewFileResult)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public Response handleGetRequest(Request request) {
        Response response = null;
        File file = getFile(request.getFileIdentificationMethod(), request.getIdentifier());
        if (!file.exists() || !file.isFile()) {
            response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.NOT_FOUND).build();
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] fileBytes = fileInputStream.readAllBytes();
                response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.SUCCESS)
                        .setFileContent(fileBytes)
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    public Response handleDeleteRequest(Request request) {
        Response response = null;
        File file = getFile(request.getFileIdentificationMethod(), request.getIdentifier());
        if (!file.exists()) {
            response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.NOT_FOUND).build();
        } else {
            if (deleteFile(file)) {
                response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.SUCCESS).build();
            } else {
                response = new Response.ResponseBuilder(request.getMethod(), CustomHttpResponse.INTERNAL_SERVER_ERROR).build();
            }
        }
        return response;
    }

    private File getFile(FileIdentificationMethod method, String identifier) {
        return switch (method) {
            case BY_ID -> getFileById(identifier);
            case BY_NAME -> getFileByName(identifier);
        };
    }

    private File getFileById(String id) {
        String fileName = fileTracker.getFiles().getOrDefault(Integer.valueOf(id), "");
        return getFileByName(fileName);
    }

    private File getFileByName(String name) {
        return new File(SERVER_FILE_DIRECTORY + name);
    }

    private Integer createNewFile(Request request, File file, FileOutputStream fileOutputStream) throws IOException {
        fileOutputStream.write(request.getFileContent());
        Integer fileId = generateFileId();
        fileTracker.getFiles().put(fileId, file.getName());
        return fileId;
    }

    private boolean deleteFile(File file) {
        if (file.delete()) {
            fileTracker.getFiles().values().remove(file.getName());
            return true;
        } else {
            return false;
        }
    }

    private Integer generateFileId() {
        Integer createdFileId = random.nextInt(65536);
        if (fileTracker.getFiles().containsKey(createdFileId)) {
            return generateFileId();
        } else {
            return createdFileId;
        }
    }

    private String getRemoteFilenameCheckForNull(Request request) {
        if (request.getRemoteFileName() == null || "".equals(request.getRemoteFileName())) {
            return generateRandomString();
        }
        return request.getRemoteFileName();
    }

    private String generateRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
