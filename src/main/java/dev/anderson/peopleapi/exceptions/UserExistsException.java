package dev.anderson.peopleapi.exceptions;

public class UserExistsException extends RuntimeException {

  public UserExistsException(String msg) {
    super(msg);
  }

}
