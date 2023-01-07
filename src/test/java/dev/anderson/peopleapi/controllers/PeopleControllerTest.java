package dev.anderson.peopleapi.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
class PeopleControllerTest {

  @Autowired
  private PeopleRepository peopleRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Get All People Should return status code 200")
  void testGetAllPeopleShouldReturnStatusCode200() throws Exception {
    mockMvc.perform(get("/api/v1/people/all?page=0&size=3"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get All People Should return json")
  void testGetAllPeopleShouldReturnJson() throws Exception {
    mockMvc.perform(get("/api/v1/people/all?page=0&size=3"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json")
        );
  }

  @Test
  @DisplayName("Get All People Should return json with one person")
  void testGetAllPeopleShouldReturnJsonWithOnePerson() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(get("/api/v1/people/all?page=0&size=3"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("$[0].name").value("Anderson"),
            MockMvcResultMatchers.jsonPath("$[0].birthDate").value("20/12/1990")
        );
  }

  @Test
  @DisplayName("Get All People header should return total elements")
  void testGetAllPeopleHeaderShouldReturnTotalElements() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(get("/api/v1/people/all?page=0&size=3"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json"),
            MockMvcResultMatchers.header().string("x-total-count", "1")
        );
  }

  @Test
  @DisplayName("Get 'findPeople' Should return status code 200")
  void testGetFindPeopleShouldReturnStatusCode200() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(get("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Get 'findPeople' Should return json")
  void testGetFindPeopleShouldReturnJson() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(get("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json")
        );
  }

  @Test
  @DisplayName("Get 'findPeople' Should return json with one person")
  void testGetFindPeopleShouldReturnJsonWithOnePerson() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(get("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpectAll(
            MockMvcResultMatchers.status().isOk(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("name").value("Anderson"),
            MockMvcResultMatchers.jsonPath("birthDate").value("20/12/1990")
        );
  }

  @Test
  @DisplayName("Get 'findPeople' Should return status code 404")
  void testGetFindPeopleShouldReturnStatusCode404() throws Exception {
    mockMvc.perform(get("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Get 'findPeople' Should return json with error message")
  void testGetFindPeopleShouldReturnJsonWithErrorMessage() throws Exception {
    mockMvc.perform(get("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpectAll(
            MockMvcResultMatchers.status().isNotFound(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("errors")
                .value("People with name: Anderson and Birth Date: 20/12/1990-> Not found")
        );
  }

  @Test
  @DisplayName("Post 'makePeople' Should return status code 201")
  void testPostMakePeopleShouldReturnStatusCode201() throws Exception {
    mockMvc.perform(post("/api/v1/people?name=teste&birthDate=20/12/1990"))
        .andExpect(status().isCreated());
  }

  @Test
  @DisplayName("Post 'makePeople' Should add one person to database")
  void testPostMakePeopleShouldAddOnePersonToDatabase() throws Exception {
    mockMvc.perform(post("/api/v1/people?name=teste&birthDate=20/12/1990"))
        .andExpect(status().isCreated());

    assertEquals(1, peopleRepository.count());
  }

  @Test
  @DisplayName("Post 'makePeople' Should return status code 400")
  void testPostMakePeopleShouldReturnStatusCode400() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("teste",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(post("/api/v1/people?name=teste&birthDate=20/12/1990"))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Post 'makePeople' Should return json with error message")
  void testPostMakePeopleShouldReturnJsonWithErrorMessage() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("teste",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(post("/api/v1/people?name=teste&birthDate=20/12/1990"))
        .andExpectAll(
            MockMvcResultMatchers.status().isBadRequest(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("errors")
                .value("People with name: teste and Birth Date: 20/12/1990-> Already Exist")
        );
  }

  @Test
  @DisplayName("Put 'updatePeople' Should return status code 200")
  void testPutUpdatePeopleShouldReturnStatusCode200() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(put("/api/v1/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"name\": \"Anderson\",\n"
                    + "    \"birthDate\": \"20/12/1990\",\n"
                    + "    \"newName\": \"C\",\n"
                    + "    \"newBirthDate\": \"20/12/1990\"\n"
                    + "}"
            ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Put 'updatePeople' Should return status code 400")
  void testPutUpdatePeopleShouldReturnStatusCode400() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(put("/api/v1/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"name\": \"Anderson\",\n"
                    + "    \"birthDate\": \"20/12/1990\",\n"
                    + "    \"newName\": \"Anderson\",\n"
                    + "    \"newBirthDate\": \"20/12/1990\"\n"
                    + "}"
            ))
        .andExpect(status().isBadRequest());
  }

  @Test
  @DisplayName("Put 'updatePeople' Should return json with error message")
  void testPutUpdatePeopleShouldReturnJsonWithErrorMessage() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(put("/api/v1/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"name\": \"Anderson\",\n"
                    + "    \"birthDate\": \"20/12/1990\",\n"
                    + "    \"newName\": \"Anderson\",\n"
                    + "    \"newBirthDate\": \"20/12/1990\"\n"
                    + "}"
            ))
        .andExpectAll(
            MockMvcResultMatchers.status().isBadRequest(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("errors")
                .value("People with name: Anderson and Birth Date: 20/12/1990-> Already Exist")
        );
  }

  @Test
  @DisplayName("Patch 'updatePeople' Should return status code 200")
  void testPatchUpdatePeopleShouldReturnStatusCode200() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(patch("/api/v1/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"name\": \"Anderson\",\n"
                    + "    \"birthDate\": \"20/12/1990\",\n"
                    + "    \"newName\": \"C\",\n"
                    + "    \"newBirthDate\": \"20/12/1990\"\n"
                    + "}"
            ))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Patch 'updatePeople' Should return status code 400")
  void testPatchUpdatePeopleShouldReturnStatusCode400() throws Exception {
    mockMvc.perform(patch("/api/v1/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"name\": \"Anderson\",\n"
                    + "    \"birthDate\": \"20/12/1990\",\n"
                    + "    \"newName\": \"Anderson\",\n"
                    + "    \"newBirthDate\": \"20/12/1990\"\n"
                    + "}"
            ))
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("Patch 'updatePeople' Should return json with error message")
  void testPatchUpdatePeopleShouldReturnJsonWithErrorMessage() throws Exception {
    mockMvc.perform(patch("/api/v1/people")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                "{\n"
                    + "    \"name\": \"Anderson\",\n"
                    + "    \"birthDate\": \"20/12/1990\",\n"
                    + "    \"newName\": \"Anderson\",\n"
                    + "    \"newBirthDate\": \"20/12/1990\"\n"
                    + "}"
            ))
        .andExpectAll(
            MockMvcResultMatchers.status().isNotFound(),
            content().contentType("application/json"),
            MockMvcResultMatchers.jsonPath("errors")
                .value("People with name: Anderson and Birth Date: 20/12/1990-> Not found")
        );
  }

  @Test
  @DisplayName("Delete 'deletePeople' Should return status code 200")
  void testDeleteDeletePeopleShouldReturnStatusCode200() throws Exception {
    PeopleEntity peopleEntity = new PeopleEntity("Anderson",
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));

    peopleRepository.save(peopleEntity);

    mockMvc.perform(delete("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("Delete 'deletePeople' Should return status code 400")
  void testDeleteDeletePeopleShouldReturnStatusCode400() throws Exception {
    mockMvc.perform(delete("/api/v1/people?name=Anderson&birthDate=20/12/1990"))
        .andExpect(status().isNotFound());
  }
}