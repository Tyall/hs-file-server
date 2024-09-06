package common;

public enum FileIdentificationMethod {
    BY_ID("BY_ID"),
    BY_NAME("BY_NAME");

    final String method;

    FileIdentificationMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
