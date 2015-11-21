package me.verto.addrmanager.integrations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.verto.addrmanager.AddrManagerApplication;
import me.verto.addrmanager.address.models.Address;
import me.verto.addrmanager.address.repositories.AddressRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AddrManagerApplication.class)
@WebAppConfiguration
public class AddressIntegrationTest {

  @Autowired
  private WebApplicationContext webAppContext;

  @Autowired
  private AddressRepository repository;

  private MockMvc mvc;

  private List<Address> addresses = new ArrayList<>();

  private Address buildAddress(String zipCode, String neighborhood, String street, String number, String city, String state) {
    Address address = new Address();
    address.setZipCode(zipCode);
    address.setNeighborhood(neighborhood);
    address.setStreet(street);
    address.setNumber(number);
    address.setCity(city);
    address.setState(state);
    return address;
  }

  @Before
  public void setUp() {
    this.mvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    addresses.add(repository.save(buildAddress("01310909","Bela Vista","Avenida Paulista 688 - Edifício Santa Filippa","10","São Paulo","SP")));
    addresses.add(repository.save(buildAddress("02011200","Santana","Rua Voluntários da Pátria","10","São Paulo","SP")));
  }

  @Test
  public void shouldReturnAllAddress() throws Exception {
    mvc.perform(get("/api/addresses"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void shouldReturnAddressById() throws Exception {
    Address address = addresses.get(0);
    mvc.perform(get("/api/addresses/" + address.getId()))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.zipCode", is(address.getZipCode().toString())));
  }

  @Test
  public void shouldCreateAddress() throws Exception {
    Address address = buildAddress("05422010", "Pinheiros", "Rua dos Pinheiros", "10", "São Paulo", "SP");

    mvc.perform(post("/api/addresses")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(json(address)))
      .andExpect(status().isCreated());
  }

  @Test
  public void shouldDeleteAddress() throws Exception {
    Address address = addresses.get(0);
    mvc.perform(delete("/api/addresses/" + address.getId()))
      .andExpect(status().isNoContent());
  }

  @Test
  public void shouldUpdateAddress() throws Exception {
    Address addressEditing = addresses.get(0);
    Address address = buildAddress("05422010", "Pinheiros", "Rua dos Pinheiros", "10", "São Paulo", "SP");

    mvc.perform(put("/api/addresses/" + addressEditing.getId())
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(json(address)))
      .andExpect(status().isNoContent());
  }

  @After
  public void tearDown() {
    repository.deleteAll();
  }

  private String json(Address address) throws Exception {
    return new ObjectMapper().writeValueAsString(address);
  }


}
