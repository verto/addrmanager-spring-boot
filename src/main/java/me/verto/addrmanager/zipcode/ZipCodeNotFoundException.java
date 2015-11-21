package me.verto.addrmanager.zipcode;

public class ZipCodeNotFoundException extends RuntimeException {

  private static final long serialVersionUID = -2122164532267503046L;

  public ZipCodeNotFoundException(String message) {
    super(message);
  }
}
