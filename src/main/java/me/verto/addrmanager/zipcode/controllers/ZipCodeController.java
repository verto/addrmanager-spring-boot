package me.verto.addrmanager.zipcode.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.verto.addrmanager.zipcode.models.ZipCode;
import me.verto.addrmanager.zipcode.models.ZipCodeResponse;

@RestController
@RequestMapping("/zipcodes")
public class ZipCodeController {

  @RequestMapping("/search")
  ZipCodeResponse search(@RequestParam(value="zipCode") String value) {
    ZipCode zipCode = new ZipCode(value);
    return new ZipCodeResponse(zipCode);
  }
}

