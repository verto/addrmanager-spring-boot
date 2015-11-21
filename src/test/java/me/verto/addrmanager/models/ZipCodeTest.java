package me.verto.addrmanager.models;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;
import me.verto.addrmanager.zipcode.models.ZipCode;

public class ZipCodeTest {

  @Test
  public void shouldCreateAValidZipCode() {
    ZipCode zipCode = new ZipCode("011310909");
    assertThat(zipCode.value(), is("011310909"));
  }

  @Test(expected=ZipCodeInvalidException.class)
  public void shouldThrowExceptionIfZipCodeIsInvalid() {
    new ZipCode("0113");
  }

  @Test(expected=ZipCodeInvalidException.class)
  public void shouldThrowExceptionIfZipCodeValueIsNull() {
    new ZipCode(null);
  }
}

