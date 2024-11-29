package com.ecommerce.authentication_service.Exceptions;

public class InvalidUserNameException extends Exception {
  public InvalidUserNameException(String msg) {
    super(msg);
  }
}
