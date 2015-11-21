package me.verto.addrmanager.zipcode.services;

import me.verto.addrmanager.zipcode.models.ZipCode;
import me.verto.addrmanager.zipcode.models.ZipCodeResponse;

public interface ZipCodeService {

  ZipCodeResponse getAddressInfo(ZipCode zipCode);

}
