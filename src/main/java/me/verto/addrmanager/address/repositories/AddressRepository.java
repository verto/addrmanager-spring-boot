package me.verto.addrmanager.address.repositories;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.repository.Repository;

import me.verto.addrmanager.address.models.Address;

public interface AddressRepository extends Repository<Address, Long> {

  Address findOne(Long id);

  List<Address> findAll();

  Address save(Address address);

  void delete(Address address);

  void deleteAll();

  default Address find(Long id) throws EntityNotFoundException {
    Address address = findOne(id);
    if (address == null) throw new EntityNotFoundException("Address " + id + " not found");
    return address;
  }

}

