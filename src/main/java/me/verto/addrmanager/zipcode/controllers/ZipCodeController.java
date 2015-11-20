package me.verto.addrmanager.zipcode.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.verto.addrmanager.zipcode.ZipCodeInvalidException;
import me.verto.addrmanager.zipcode.models.ZipCodeResponse;

@RestController
@RequestMapping("/zipcodes")
public class ZipCodeController {

  @RequestMapping("/search")
  ZipCodeResponse search(@RequestParam(value="zipCode") String zipCode) {
    if ("0113".equals(zipCode)) throw new ZipCodeInvalidException("zipcode is invalid");

    return new ZipCodeResponse(zipCode);
  }
}

