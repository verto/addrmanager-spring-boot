package me.verto.addrmanager.zipcode.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.verto.addrmanager.zipcode.ZipCodeNotFoundException;
import me.verto.addrmanager.zipcode.models.ZipCode;
import me.verto.addrmanager.zipcode.models.ZipCodeResponse;
import me.verto.addrmanager.zipcode.services.ZipCodeGateway;
import me.verto.addrmanager.zipcode.services.ZipCodeService;

@Service
public class ZipCodeServiceImpl implements ZipCodeService {

  private ZipCodeGateway gateway;

  @Autowired
  public ZipCodeServiceImpl(ZipCodeGateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public ZipCodeResponse getAddressInfo(ZipCode zipCode) {
    ZipCodeResponse response = gateway.request(zipCode.value());

    if (response == null && zipCode.hasNext()) {
      response = this.getAddressInfo(zipCode.next());
    }

    if (response == null) {
      throw new ZipCodeNotFoundException("address not found");
    }

    return response;
  }
}
