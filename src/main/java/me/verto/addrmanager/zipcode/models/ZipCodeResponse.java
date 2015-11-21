package me.verto.addrmanager.zipcode.models;

import java.io.Serializable;

public class ZipCodeResponse implements Serializable {

  private static final long serialVersionUID = -7248269819611336175L;

  private String zipCode;
  private String neighborhood;
  private String street;
  private String city;
  private String state;

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

}
