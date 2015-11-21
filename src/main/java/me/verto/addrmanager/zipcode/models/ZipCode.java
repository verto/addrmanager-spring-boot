package me.verto.addrmanager.zipcode.models;

import java.io.Serializable;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonValue;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;

public class ZipCode implements Serializable {

  private static final String VALID_VALUE = "\\d{8}";

  private static final long serialVersionUID = 635841783928141496L;

  private final String value;

  public ZipCode(final String value) {
    if (StringUtils.isEmpty(value) || !value.matches(VALID_VALUE)) throw new ZipCodeInvalidException("zipcode is invalid");
    this.value = value;
  }

  @JsonValue
  public String value() {
    return value;
  }

  public ZipCode next() {
    String nextValue = this.value.replaceAll("[1-9](?!.*[1-9])", "0");
    return new ZipCode(nextValue);
  }

  public boolean hasNext() {
    return !"00000000".equals(this.value);
  }

  @Override
  public String toString() {
    return value();
  }

}
