package me.verto.addrmanager.zipcode;

public class ZipCodeInvalidException extends RuntimeException {
  private static final long serialVersionUID = 7257703582558932000L;

  public ZipCodeInvalidException(String message) {
    super(message);
  }
}
