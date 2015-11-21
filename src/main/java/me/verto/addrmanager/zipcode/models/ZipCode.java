package me.verto.addrmanager.zipcode.models;

import java.io.Serializable;
import org.springframework.util.StringUtils;
import me.verto.addrmanager.zipcode.ZipCodeInvalidException;

public class ZipCode implements Serializable {

  private static final String VALID_VALUE = "\\d{9}";

  private static final long serialVersionUID = 635841783928141496L;

  private final String value;

  public ZipCode(final String value) {
    if (StringUtils.isEmpty(value) || !value.matches(VALID_VALUE)) throw new ZipCodeInvalidException("zipcode is invalid");
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return value();
  }

}
