package p_henriqued.javafxstudy.infra.Exceptions;

import java.io.Serial;
import java.util.HashMap;

public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private HashMap<String, String> error = new HashMap<>();

    public ValidationException(String message) {
        super(message);
    }
    public ValidationException(String fieldName, String errorMessage){
        super(errorMessage);
        this.error.put(fieldName, errorMessage);
    }

    public void addError(String fieldName, String errorMessage){
        error.put(fieldName, errorMessage);
    }

    public HashMap<String, String> getError() {
        return error;
    }

}
