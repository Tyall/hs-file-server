package common;

public enum CustomHttpRequest {
    GET("GET"),
    PUT("PUT"),
    DELETE("DELETE"),
    EXIT("EXIT");

    private final String method;

    CustomHttpRequest(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}
