package me.verto.addrmanager.zipcode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.verto.addrmanager.zipcode.models.ZipCode;
import me.verto.addrmanager.zipcode.models.ZipCodeResponse;
import me.verto.addrmanager.zipcode.services.ZipCodeService;

@RestController
@RequestMapping("/zipcodes")
public class ZipCodeController {

  private ZipCodeService zipCodeService;

  @Autowired
  public ZipCodeController(ZipCodeService zipCodeService) {
    this.zipCodeService = zipCodeService;
  }

  @RequestMapping("/search")
  ZipCodeResponse search(@RequestParam(value="zipCode") String value) {
    return zipCodeService.getAddressInfo(new ZipCode(value));
  }
}

