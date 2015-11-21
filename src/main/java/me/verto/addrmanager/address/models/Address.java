package me.verto.addrmanager.address.models;

import java.io.Serializable;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import me.verto.addrmanager.zipcode.models.ZipCode;
import me.verto.addrmanager.zipcode.repositories.ZipCodeConverter;

@Entity
public class Address implements Serializable {

  private static final long serialVersionUID = 7127765304463339600L;

  @Id @GeneratedValue
  private Long id;

  @NotNull @Convert(converter = ZipCodeConverter.class)
  private ZipCode zipCode;

  @NotNull private String street;
  @NotNull private String number;
  private String neighborhood;
  private String additionalInfo;
  @NotNull private String city;
  @NotNull private String state;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ZipCode getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = new ZipCode(zipCode);
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getNeighborhood() {
    return neighborhood;
  }

  public void setNeighborhood(String neighborhood) {
    this.neighborhood = neighborhood;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getAdditionalInfo() {
    return additionalInfo;
  }

  public void setAdditionalInfo(String additionalInfo) {
    this.additionalInfo = additionalInfo;
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

  public Address update(final Address data) {
    BeanUtils.copyProperties(data, this, "id");
    return this;
  }

}
