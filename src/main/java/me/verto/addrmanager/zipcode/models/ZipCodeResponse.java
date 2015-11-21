package me.verto.addrmanager.zipcode.models;

import java.io.Serializable;

public class ZipCodeResponse implements Serializable {

  private static final long serialVersionUID = -7248269819611336175L;

  private String zipCode;

  public ZipCodeResponse() {  }

  public ZipCodeResponse(ZipCode zipCode) {
    this.zipCode = zipCode.value();
  }

  public String getZipCode() {
    return zipCode;
  }

}
