package common;

import java.util.HashMap;
import java.util.Map;

public enum CustomHttpResponse {
    SUCCESS(200),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final Integer code;

    CustomHttpResponse(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    private static final Map<Integer, CustomHttpResponse> byCode = new HashMap<>();
    static {
        for (CustomHttpResponse e : CustomHttpResponse.values()) {
            if (byCode.put(e.getCode(), e) != null) {
                throw new IllegalArgumentException("duplicate id: " + e.getCode());
            }
        }
    }

    public static CustomHttpResponse getByCode(Integer code) {
        return byCode.get(code);
    }
}
