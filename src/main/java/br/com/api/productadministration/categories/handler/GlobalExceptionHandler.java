package br.com.api.productadministration.categories.handler;

import br.com.api.productadministration.categories.handler.error.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Object> CategoryNotFoundException(EntityNotFoundException ex){
    return buildResponseEntity(HttpStatus.NOT_FOUND,
            ex.getMessage(),
            Collections.singletonList(ex.getCause().toString()));
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {
    List<String> errors = new ArrayList<>();
    ex.getBindingResult()
            .getFieldErrors()
            .forEach(fieldError -> errors.add("Field " + fieldError.getField().toUpperCase() + " " + fieldError.getDefaultMessage()));
    ex.getBindingResult()
            .getGlobalErrors()
            .forEach(globalError -> errors.add("Object " + globalError.getObjectName() + " " + globalError.getDefaultMessage()));

    return buildResponseEntity(HttpStatus.BAD_REQUEST, "Informed argument(s) validation error(s)", errors);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                HttpHeaders headers,
                                                                HttpStatusCode status,
                                                                WebRequest request) {
    return buildResponseEntity(HttpStatus.BAD_REQUEST, "Malformed JSON body and/or field error",
            Collections.singletonList(ex.getLocalizedMessage()));
  }

  private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String message, List<String> errors){
    ErrorResponse error = new ErrorResponse.Builder()
            .withCode(httpStatus.value())
            .withStatus(httpStatus.getReasonPhrase())
            .withMessage(message)
            .withTimestamp(LocalDateTime.now())
            .withErrors(errors)
            .build();
    return ResponseEntity.status(httpStatus).body(error);

  }
}
