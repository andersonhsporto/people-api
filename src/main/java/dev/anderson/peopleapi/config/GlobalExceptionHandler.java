package dev.anderson.peopleapi.config;

import dev.anderson.peopleapi.exceptions.UserExistsException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserExistsException.class)
  public ResponseEntity<Map<String, List<String>>> handleUserExistsException(UserExistsException ex) {
    List<String> errors = Collections.singletonList(ex.getMessage());

    return new ResponseEntity<>(getErrorsMap(errors),  new HttpHeaders(), HttpStatus.BAD_REQUEST);

  }

  private Map<String, List<String>> getErrorsMap(List<String> errors) {
    Map<String, List<String>> errorResponse = new HashMap<>();
    errorResponse.put("errors", errors);
    return errorResponse;
  }

}
