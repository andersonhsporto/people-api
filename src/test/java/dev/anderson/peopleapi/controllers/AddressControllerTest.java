package dev.anderson.peopleapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.anderson.peopleapi.domain.entities.AddressEntity;
import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(properties = "spring.main.banner-mode=off")
@Transactional
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AddressControllerTest {

  @Autowired
  private PeopleRepository peopleRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Get All Addresses Should return status code 200")
  void testGetAllAddressesShouldReturnStatusCode200() throws Exception {
    mockMvc.perform(get("/api/v1/address/all"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get All Addresses Should return json")
  void testGetAllAddressesShouldReturnJson() throws Exception {
    mockMvc.perform(get("/api/v1/address/all"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json")
        );
  }

  @Test
  @DisplayName("Get All Addresses Should return json with one address")
  void testGetAllAddressesShouldReturnJsonWithOneAddress() throws Exception {
    var addressEntity = new AddressEntity("Rua 1", "1", "São Paulo", "04001-000");
    var peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")),
        List.of(addressEntity));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(get("/api/v1/address/all"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$[0].publicPlace").value("Rua 1"),
            MockMvcResultMatchers.jsonPath("$[0].number").value("1"),
            MockMvcResultMatchers.jsonPath("$[0].city").value("São Paulo"),
            MockMvcResultMatchers.jsonPath("$[0].cep").value("04001-000")
        );
  }

  @Test
  @DisplayName("Post Address Should return status code 201")
  void testPostAddressShouldReturnStatusCode201() throws Exception {
    var peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));
    peopleRepository.save(peopleEntity);

    mockMvc.perform(post("/api/v1/address?name=Anderson&birthDate=20/12/1990")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"publicPlace\": \"Rua Amelia\",\n"
                    + "    \"number\": \"1254\",\n"
                    + "    \"city\": \"São Paulo\",\n"
                    + "    \"cep\": \"04181-150\"\n"
                    + "}"
            ))
        .andExpect(status().isCreated());

  }

  @Test
  @DisplayName("Post Address Should return status code 404")
  void testPostAddressShouldReturnStatusCode404() throws Exception {
    mockMvc.perform(post("/api/v1/address?name=Anderson&birthDate=20/12/1990")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"publicPlace\": \"Rua Amelia\",\n"
                    + "    \"number\": \"1254\",\n"
                    + "    \"city\": \"São Paulo\",\n"
                    + "    \"cep\": \"04181-150\"\n"
                    + "}"
            ))
        .andExpect(status().isNotFound());

  }

}