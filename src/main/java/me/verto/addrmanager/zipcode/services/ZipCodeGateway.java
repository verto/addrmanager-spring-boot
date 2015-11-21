package me.verto.addrmanager.zipcode.services;

import me.verto.addrmanager.zipcode.models.ZipCodeResponse;

public interface ZipCodeGateway {
  ZipCodeResponse request(String zipCode);
}
