package com.postservice.Exception;

import com.postservice.Errors.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler({PostNotFoundException.class})
    ResponseEntity customerNotFoundHandler(Exception exception, ServletWebRequest request){
        ApiError apiError = new ApiError();
        apiError.setMessage(exception.getMessage());
        apiError.setCode("404");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        {
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            List<String> errors = fieldErrors.stream()
                    .map(err -> err.getField() + " : " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            ApiError apiError = new ApiError();
            apiError.setCode(String.valueOf(status.value()));
            apiError.setMessage(String.valueOf(errors));
//            apiError.setErrors(errors);
//            apiError.setPath(request.getDescription(false));
//            apiError.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
        }
    }

}
