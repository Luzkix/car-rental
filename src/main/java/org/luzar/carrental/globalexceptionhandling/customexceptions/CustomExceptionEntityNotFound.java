package org.luzar.carrental.globalexceptionhandling.customexceptions;

public class CustomExceptionEntityNotFound extends RuntimeException {

  public CustomExceptionEntityNotFound(String message) {
    super(message);
  }
}