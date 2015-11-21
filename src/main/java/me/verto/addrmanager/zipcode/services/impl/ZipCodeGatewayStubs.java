package me.verto.addrmanager.zipcode.services.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.verto.addrmanager.zipcode.models.ZipCodeResponse;
import me.verto.addrmanager.zipcode.services.ZipCodeGateway;

@Service
public class ZipCodeGatewayStubs implements ZipCodeGateway {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private static Map<String, String> zipCodes = new HashMap<String, String>();

  static {
    zipCodes.put("02011200", "{ \"neighborhood\": \"Santana\", \"street\": \"Rua Voluntários da Pátria\", \"zipCode\": \"02011200\", \"state\": \"SP\", \"city\": \"São Paulo\" }");
    zipCodes.put("01310909", "{ \"neighborhood\": \"Bela Vista\", \"street\": \"Avenida Paulista\", \"zipCode\": \"01310909\", \"state\": \"SP\", \"city\": \"São Paulo\" }");
    zipCodes.put("05422010", "{ \"neighborhood\": \"Pinheiros\", \"street\": \"Rua dos Pinheiros\", \"zipCode\": \"05422010\", \"state\": \"SP\", \"city\": \"São Paulo\" }");
  }

  @Override
  public ZipCodeResponse request(String zipCode) {

    String jsonContent = zipCodes.get(zipCode);
    if (jsonContent == null) return null;

    try {

      return new ObjectMapper().readValue(jsonContent, ZipCodeResponse.class);

    } catch (IOException ex) {
      log.error("cannot possible to create ZipCodeResponse object.", ex);
      return null;
    }

  }
}
