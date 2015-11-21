package me.verto.addrmanager.models;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;
import me.verto.addrmanager.zipcode.models.ZipCode;

public class ZipCodeTest {

  @Test
  public void shouldCreateAValidZipCode() {
    ZipCode zipCode = new ZipCode("01310909");
    assertThat(zipCode.value(), is("01310909"));
  }

  @Test(expected=ZipCodeInvalidException.class)
  public void shouldThrowExceptionIfZipCodeIsInvalid() {
    new ZipCode("0113");
  }

  @Test(expected=ZipCodeInvalidException.class)
  public void shouldThrowExceptionIfZipCodeValueIsNull() {
    new ZipCode(null);
  }

  @Test
  public void shouldReturnNextZipCodeReplacingRightDigitsDifferentOfZeroToZero() {
    ZipCode zipCode = new ZipCode("01310909");
    assertThat(zipCode.next().value(), is("01310900"));
  }

  @Test
  public void shouldReturnNextZipCodeReplacingDigitsDiffOfZeroUntilFirstDigit() {
    ZipCode zipCode = new ZipCode("10000000");
    assertThat(zipCode.next().value(), is("00000000"));
  }

  @Test
  public void shouldReturnTrueIfZipCodeHasNextPossibilityOfDigitsWithZeros() {
    ZipCode zipCode = new ZipCode("10000000");
    assertThat(zipCode.hasNext(), is(true));
  }

  @Test
  public void shouldReturnFalseIfZipCodeHasOnlyZerosDigits() {
    ZipCode zipCode = new ZipCode("00000000");
    assertThat(zipCode.hasNext(), is(false));
  }
}

