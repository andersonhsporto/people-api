package dev.anderson.peopleapi.service.implementation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import dev.anderson.peopleapi.domain.DTO.PeopleInputDTO;
import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.exceptions.UserExistsException;
import dev.anderson.peopleapi.exceptions.UserNotFoundException;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(properties = "spring.main.banner-mode=off")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class PeopleServiceImplementationTest {

  @Autowired
  private PeopleServiceImplementation peopleServiceImplementation;

  @Autowired
  private PeopleRepository peopleRepository;

  @Test
  @DisplayName("listAll Should return status code 200")
  void testListAllShouldReturnStatusCode200() {
    var page = 0;
    var size = 10;
    var response = peopleServiceImplementation.listAll(page, size);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("listAll Should return empty json")
  void testListAllShouldReturnEmptyJson() {
    var page = 0;
    var size = 10;
    var response = peopleServiceImplementation.listAll(page, size);

    assertThat(response.getBody().toString()).contains("[]");
  }

  @Test
  @DisplayName("listAll Should return json with data from database")
  void testListAllShouldReturnJsonWithDataFromDatabase() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Anderson", birthDate);

    peopleRepository.save(peopleEntity);

    var page = 0;
    var size = 10;
    var response = peopleServiceImplementation.listAll(page, size);

    assertThat(response.getBody().toString()).contains("Anderson");
    assertThat(response.getBody().toString()).contains("20/12/1990");
  }

  @Test
  @DisplayName("listAll Should return page with size 2")
  void testListAllShouldReturnPageWithSize2() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Anderson", birthDate);
    var peopleEntity2 = new PeopleEntity("Anderson2", birthDate);
    var peopleEntity3 = new PeopleEntity("Anderson3", birthDate);

    peopleRepository.save(peopleEntity);
    peopleRepository.save(peopleEntity2);
    peopleRepository.save(peopleEntity3);

    var page = 0;
    var size = 2;
    var response = peopleServiceImplementation.listAll(page, size);

    assertThat(response.getBody().toString()).contains("Anderson");
    assertThat(response.getBody().toString()).contains("Anderson2");
    assertThat(response.getBody().toString()).doesNotContain("Anderson3");
  }

  @Test
  @DisplayName("listAll Should return page with size 1 and page 1")
  void testListAllShouldReturnPageWithSize2AndPage1() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);
    var peopleEntity2 = new PeopleEntity("Anderson", birthDate);
    var peopleEntity3 = new PeopleEntity("Pedro", birthDate);

    peopleRepository.save(peopleEntity);
    peopleRepository.save(peopleEntity2);
    peopleRepository.save(peopleEntity3);

    var page = 1;
    var size = 1;
    var response = peopleServiceImplementation.listAll(page, size);

    assertThat(response.getBody().toString()).doesNotContain("Ana");
    assertThat(response.getBody().toString()).contains("Anderson");
    assertThat(response.getBody().toString()).doesNotContain("Pedro");
  }

  @Test
  @DisplayName("listAll Should header with total elements")
  void testListAllShouldHeaderWithTotalElements() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);
    var peopleEntity2 = new PeopleEntity("Anderson", birthDate);
    var peopleEntity3 = new PeopleEntity("Pedro", birthDate);

    peopleRepository.save(peopleEntity);
    peopleRepository.save(peopleEntity2);
    peopleRepository.save(peopleEntity3);

    var page = 0;
    var size = 1;
    var response = peopleServiceImplementation.listAll(page, size);

    assertThat(response.getHeaders().get("x-total-count").get(0)).isEqualTo("3");
  }

  @Test
  @DisplayName("findPeople Should return status code 200")
  void testFindPeopleShouldReturnStatusCode200() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);
    peopleRepository.save(peopleEntity);

    var response = peopleServiceImplementation.findPeople("Ana", "20/12/1990");

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("findPeople Should throw exception when people not found")
  void testFindPeopleShouldThrowExceptionWhenPeopleNotFound() {
    try {
      peopleServiceImplementation.findPeople("Ana", "20/12/1990");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Not found");
    }
  }

  @Test
  @DisplayName("findPeople Should return json with data from database")
  void testFindPeopleShouldReturnJsonWithDataFromDatabase() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);
    peopleRepository.save(peopleEntity);

    var response = peopleServiceImplementation.findPeople("Ana", "20/12/1990");

    assertThat(response.getBody().toString()).contains("Ana");
    assertThat(response.getBody().toString()).contains("20/12/1990");
  }

  @Test
  @DisplayName("findPeople Should throw exception when date is invalid")
  void testFindPeopleShouldThrowExceptionWhenDateIsInvalid() {
    try {
      peopleServiceImplementation.findPeople("Ana", "invalid date");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo("Text 'invalid date' could not be parsed at index 0");
      assertThat(e.getClass()).isEqualTo(DateTimeParseException.class);
    }
  }

  @Test
  @DisplayName("makePeople Should return status code 201")
  void testMakePeopleShouldReturnStatusCode201() {
    var response = peopleServiceImplementation.makePeople("Ana", "20/12/1990");

    assertThat(response.getStatusCode().value()).isEqualTo(201);
  }

  @Test
  @DisplayName("makePeople Should throw exception when people already exists")
  void testMakePeopleShouldThrowExceptionWhenPeopleAlreadyExists() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);
    peopleRepository.save(peopleEntity);

    try {
      peopleServiceImplementation.makePeople("Ana", "20/12/1990");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Already Exist");
    }
  }

  @Test
  @DisplayName("makePeople Should throw exception when date is invalid")
  void testMakePeopleShouldThrowExceptionWhenDateIsInvalid() {
    try {
      peopleServiceImplementation.makePeople("Ana", "invalid date");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo("Text 'invalid date' could not be parsed at index 0");
      assertThat(e.getClass()).isEqualTo(DateTimeParseException.class);
    }
  }

  @Test
  @DisplayName("replacePeople Should return status code 200")
  void testReplacePeopleShouldReturnStatusCode200() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Anderson", "01/01/2000");
    var response = peopleServiceImplementation.replacePeople(inputDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("replacePeople Should throw exception when people not found")
  void testReplacePeopleShouldThrowExceptionWhenPeopleNotFound() {
    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Anderson", "01/01/2000");
    try {
      peopleServiceImplementation.replacePeople(inputDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Not found");
    }
  }

  @Test
  @DisplayName("replacePeople Should throw exception when date is invalid")
  void testReplacePeopleShouldThrowExceptionWhenDateIsInvalid() {
    var inputDTO = new PeopleInputDTO("Ana", "invalid date", "Anderson", "01/01/2000");
    try {
      peopleServiceImplementation.replacePeople(inputDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo("Text 'invalid date' could not be parsed at index 0");
      assertThat(e.getClass()).isEqualTo(DateTimeParseException.class);
    }
  }

  @Test
  @DisplayName("replacePeople change name and birth date")
  void testReplacePeopleChangeNameAndBirthDate() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Anderson", "01/01/2000");
    var response = peopleServiceImplementation.replacePeople(inputDTO);
    var newPeopleEntity = peopleRepository.findById(1L).get();

    assertThat(newPeopleEntity.getName()).isEqualTo("Anderson");
    assertThat(newPeopleEntity.getBirthDate()).isEqualTo(
        LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("d/MM/yyyy")));
  }

  @Test
  @DisplayName("replacePeople Should not change name and birth date")
  void testReplacePeopleShouldNotChangeNameAndBirthDate() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Ana", "20/12/1990");
    try {
      peopleServiceImplementation.replacePeople(inputDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Already Exist");
      assertThat(e.getClass()).isEqualTo(UserExistsException.class);
    }
  }

  @Test
  @DisplayName("updatePeople Should return status code 200")
  void testUpdatePeopleShouldReturnStatusCode200() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Anderson", "01/01/2000");
    var response = peopleServiceImplementation.updatePeople(inputDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("updatePeople Should throw exception when people not found")
  void testUpdatePeopleShouldThrowExceptionWhenPeopleNotFound() {
    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Anderson", "01/01/2000");
    try {
      peopleServiceImplementation.updatePeople(inputDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Not found");
      assertThat(e.getClass()).isEqualTo(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("updatePeople Should throw exception when date is invalid")
  void testUpdatePeopleShouldThrowExceptionWhenDateIsInvalid() {
    var inputDTO = new PeopleInputDTO("Ana", "invalid date", "Anderson", "01/01/2000");
    try {
      peopleServiceImplementation.updatePeople(inputDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo("Text 'invalid date' could not be parsed at index 0");
      assertThat(e.getClass()).isEqualTo(DateTimeParseException.class);
    }
  }

  @Test
  @DisplayName("updatePeople should only change name")
  void testUpdatePeopleShouldOnlyChangeName() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Anderson", "20/12/1990");
    var response = peopleServiceImplementation.updatePeople(inputDTO);
    var newPeopleEntity = peopleRepository.findById(1L).get();

    assertThat(newPeopleEntity.getName()).isEqualTo("Anderson");
    assertThat(newPeopleEntity.getBirthDate()).isEqualTo(
        LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy")));
  }

  @Test
  @DisplayName("updatePeople should only change birth date")
  void testUpdatePeopleShouldOnlyChangeBirthDate() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Ana", "01/01/2000");
    var response = peopleServiceImplementation.updatePeople(inputDTO);
    var newPeopleEntity = peopleRepository.findById(1L).get();

    assertThat(newPeopleEntity.getName()).isEqualTo("Ana");
    assertThat(newPeopleEntity.getBirthDate()).isEqualTo(
        LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("d/MM/yyyy")));
  }

  @Test
  @DisplayName("updatePeople Should not change name and birth date")
  void testUpdatePeopleShouldNotChangeNameAndBirthDate() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var inputDTO = new PeopleInputDTO("Ana", "20/12/1990", "Ana", "20/12/1990");
    try {
      peopleServiceImplementation.updatePeople(inputDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Already Exist");
      assertThat(e.getClass()).isEqualTo(UserExistsException.class);
    }
  }

  @Test
  @DisplayName("deletePeople Should return status code 200")
  void testDeletePeopleShouldReturnStatusCode200() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    var response = peopleServiceImplementation.deletePeople("Ana", "20/12/1990");

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("deletePeople Should throw exception when people not found")
  void testDeletePeopleShouldThrowExceptionWhenPeopleNotFound() {
    try {
      peopleServiceImplementation.deletePeople("Ana", "20/12/1990");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Ana and Birth Date: 20/12/1990-> Not found");
      assertThat(e.getClass()).isEqualTo(UserNotFoundException.class);
    }
  }

  @Test
  @DisplayName("deletePeople Should throw exception when date is invalid")
  void testDeletePeopleShouldThrowExceptionWhenDateIsInvalid() {
    try {
      peopleServiceImplementation.deletePeople("Ana", "invalid date");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo("Text 'invalid date' could not be parsed at index 0");
      assertThat(e.getClass()).isEqualTo(DateTimeParseException.class);
    }
  }

  @Test
  @DisplayName("deletePeople Should delete people")
  void testDeletePeopleShouldDeletePeople() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Ana", birthDate);

    peopleRepository.save(peopleEntity);

    peopleServiceImplementation.deletePeople("Ana", "20/12/1990");

    assertThat(peopleRepository.findById(1L)).isEmpty();
  }


}