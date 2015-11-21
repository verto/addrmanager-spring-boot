package me.verto.addrmanager.services;

import org.junit.Before;
import org.junit.Test;

import me.verto.addrmanager.zipcode.ZipCodeNotFoundException;
import me.verto.addrmanager.zipcode.models.ZipCode;
import me.verto.addrmanager.zipcode.models.ZipCodeResponse;
import me.verto.addrmanager.zipcode.services.ZipCodeGateway;
import me.verto.addrmanager.zipcode.services.ZipCodeService;
import me.verto.addrmanager.zipcode.services.impl.ZipCodeServiceImpl;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ZipCodeServiceTest {

  private ZipCodeService zipCodeService;
  private ZipCodeGateway gateway;

  @Before
  public void setUp() { 
    this.gateway = mock(ZipCodeGateway.class);
    this.zipCodeService = new ZipCodeServiceImpl(gateway);
  }

  @Test
  public void shouldGetAdressInfoByZipCode() {
    ZipCode zipcode = new ZipCode("01310909");
    ZipCodeResponse response = new ZipCodeResponse();

    when(gateway.request(zipcode.value())).thenReturn(response);

    assertThat(this.zipCodeService.getAddressInfo(zipcode), is(response));
  }

  @Test(expected=ZipCodeNotFoundException.class)
  public void shouldthrowZipCodeNotFoundExceptionIfDoesntExistsAddressInfo() {
    ZipCode zipcode = new ZipCode("01310900");

    when(gateway.request(zipcode.value())).thenReturn(null);

    this.zipCodeService.getAddressInfo(zipcode);
  }

  @Test
  public void shouldChangeDigitsFromRightToLeftWithZeroAndRequestAddressInfo() {
    ZipCode zipcode = new ZipCode("01310909");
    ZipCodeResponse response = new ZipCodeResponse();

    when(gateway.request("01310909")).thenReturn(null);
    when(gateway.request("01310900")).thenReturn(null);
    when(gateway.request("01310000")).thenReturn(response);

    assertThat(this.zipCodeService.getAddressInfo(zipcode), is(response));
  }
}
