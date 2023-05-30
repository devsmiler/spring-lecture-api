package member.week1.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class CustomException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();
    public CustomException(String message) {
        super(message);
    }
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
    public abstract int statusCode();
    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
