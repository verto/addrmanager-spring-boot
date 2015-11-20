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
public class ZipCodeTests {

  @Autowired
  private WebApplicationContext webAppContext;

  private MockMvc mvc;

  @Before
  public void setUp() {
    this.mvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
  }

  @Test
  public void shouldReturnAddressContentIfExistsForValidCep() throws Exception {
    mvc.perform(get("/zipcodes/search?zipCode=011310909"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.zipCode", is("011310909")));
  }

  @Test
  public void shouldReturnInvalidErrorIfCepIsInvalid() throws Exception {
    mvc.perform(get("/zipcodes/search?zipCode=0113"))
      .andExpect(status().isUnprocessableEntity())
      .andExpect(jsonPath("$.errorMessage", is("zipcode is invalid")));
  }

  @Test
  public void shouldReturnNotFoundMessageIfCepIsValidButWihoutAddress() {
  }
}
