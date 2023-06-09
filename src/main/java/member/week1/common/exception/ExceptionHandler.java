package member.week1.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionHandler { // FIXME ClassName should be more specific such as Week1ControllerAdvice

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> DeanExceptionHandler(CustomException e) { // FIXME Method name should be verb
        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(e.statusCode()))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();
        ResponseEntity<ErrorResponse> response = ResponseEntity.status(e.statusCode())
                .body(body);
        return response;

    }
}
