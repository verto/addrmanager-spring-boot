package me.verto.addrmanager.integrations;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import me.verto.addrmanager.AddrManagerApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AddrManagerApplication.class)
@WebAppConfiguration
public class ZipCodeIntegrationTest {

  @Autowired
  private WebApplicationContext webAppContext;

  private MockMvc mvc;

  @Before
  public void setUp() {
    this.mvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  public void shouldReturnAddressContentIfExistsForValidCep() throws Exception {
    mvc.perform(get("/api/zipcodes/search?zipCode=01310909"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.zipCode", is("01310909")));
  }

  @Test
  public void shouldReturnInvalidErrorIfCepIsInvalid() throws Exception {
    mvc.perform(get("/api/zipcodes/search?zipCode=0113"))
      .andExpect(status().isBadRequest())
      .andExpect(jsonPath("$.errors[0].message", is("zipcode is invalid")));
  }

  @Test
  public void shouldReturnNotFoundMessageIfCepIsValidButWihoutAddress() throws Exception {
    mvc.perform(get("/api/zipcodes/search?zipCode=01312920"))
      .andExpect(status().isUnprocessableEntity())
      .andExpect(jsonPath("$.errors[0].message", is("address not found")));
  }

  @Test
  public void shouldReturnAddressContentToNextValidCep() throws Exception {
    mvc.perform(get("/api/zipcodes/search?zipCode=02011201"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.zipCode", is("02011200")));
  }
}
