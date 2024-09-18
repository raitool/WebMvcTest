package ee.ooloros.hibernate.demo1321.shared;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ControllerAdvice
public class SharedControllerAdvice {

    public record ApiError(String code, Map<String, Object> params) {

    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)  // 409
    @ExceptionHandler(EntityNotFoundException.class)
    public @ResponseBody ApiError entityNotFound() {
        return new ApiError("common.not_found", Map.of());
    }
}
