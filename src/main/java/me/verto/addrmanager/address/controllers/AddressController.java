package me.verto.addrmanager.address.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.List;

import javax.validation.Valid;

import me.verto.addrmanager.address.models.Address;
import me.verto.addrmanager.address.repositories.AddressRepository;

@RestController
@RequestMapping("/addresses")
public class AddressController {

  private AddressRepository addressRepository;

  @Autowired
  public AddressController(AddressRepository repository) {
    this.addressRepository = repository;
  }

  @RequestMapping(method=GET)
  List<Address> index() {
    return addressRepository.findAll();
  }

  @RequestMapping(method=POST)
  @ResponseStatus(HttpStatus.CREATED)
  void create(@Valid @RequestBody Address address) {
    addressRepository.save(address);
  }

  @RequestMapping(value="{id}", method=GET)
  Address show(@PathVariable Long id) {
    return addressRepository.find(id);
  }

  @RequestMapping(value="{id}", method=PUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void update(@PathVariable Long id, @Valid @RequestBody Address addressData) {
    Address address = addressRepository.find(id);
    addressRepository.save(address.update(addressData));
  }

  @RequestMapping(value="{id}", method=DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void destroy(@PathVariable Long id) {
    Address address = addressRepository.find(id);
    addressRepository.delete(address);
  }

}
