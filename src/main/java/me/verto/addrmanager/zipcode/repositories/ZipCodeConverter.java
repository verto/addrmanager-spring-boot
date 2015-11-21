package me.verto.addrmanager.zipcode.repositories;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import me.verto.addrmanager.zipcode.models.ZipCode;

@Converter
public class ZipCodeConverter implements AttributeConverter<ZipCode, String> {

  @Override
  public String convertToDatabaseColumn(ZipCode zipCode) {
    return zipCode.value();
  }

  @Override
  public ZipCode convertToEntityAttribute(String value) {
    return new ZipCode(value);
  }
}
