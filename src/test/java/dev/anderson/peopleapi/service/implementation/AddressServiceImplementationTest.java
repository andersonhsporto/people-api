package dev.anderson.peopleapi.service.implementation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import dev.anderson.peopleapi.domain.DTO.AddressDTO;
import dev.anderson.peopleapi.domain.entities.AddressEntity;
import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.repositories.AddressRepository;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


@SpringBootTest(properties = "spring.main.banner-mode=off")
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
class AddressServiceImplementationTest {

  @Autowired
  private AddressServiceImplementation addressServiceImplementation;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private PeopleRepository peopleRepository;

  @Test
  @DisplayName("listAll Should return status code 200")
  void testListAllShouldReturnStatusCode200() {
    ResponseEntity<?> response = addressServiceImplementation.listAll();

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("listAll Should return json with all addresses")
  void testListAllShouldReturnJsonWithAllAddresses() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var addressEntity = new AddressEntity("Rua 1", "1", "São Paulo", "04001-000");
    var peopleEntity = new PeopleEntity("Anderson", birthDate, List.of(addressEntity));

    peopleRepository.save(peopleEntity);

    var response = addressServiceImplementation.listAll();

    assertThat(response.getBody().toString()).contains("Rua 1");
    assertThat(response.getBody().toString()).contains("1");
    assertThat(response.getBody().toString()).contains("São Paulo");
    assertThat(response.getBody().toString()).contains("04001-000");
  }

  @Test
  @DisplayName("listAll Should return empty json")
  void testListAllShouldReturnEmptyJson() {
    var response = addressServiceImplementation.listAll();

    assertThat(response.getBody().toString()).isEqualTo("[]");
  }

  @Test
  @DisplayName("findAddress Should return status code 200")
  void testFindAddressShouldReturnStatusCode200() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var addressEntity = new AddressEntity("Rua 1", "1", "São Paulo", "04001-000");
    var peopleEntity = new PeopleEntity("Anderson", birthDate, List.of(addressEntity));

    peopleRepository.save(peopleEntity);

    var response = addressServiceImplementation.findAddress("Anderson", "20/12/1990");

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("findAddress Should return json with address")
  void testFindAddressShouldReturnJsonWithAddress() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var addressEntity = new AddressEntity("Rua 1", "1", "São Paulo", "04001-000");
    var peopleEntity = new PeopleEntity("Anderson", birthDate, List.of(addressEntity));

    peopleRepository.save(peopleEntity);

    var response = addressServiceImplementation.findAddress("Anderson", "20/12/1990");

    assertThat(response.getBody().toString()).contains("Rua 1");
    assertThat(response.getBody().toString()).contains("1");
    assertThat(response.getBody().toString()).contains("São Paulo");
    assertThat(response.getBody().toString()).contains("04001-000");
  }

  @Test
  @DisplayName("findAddress Should return empty json")
  void testFindAddressShouldReturnEmptyJson() {
    makeTempPeople();

    var response = addressServiceImplementation.findAddress("Anderson", "20/12/1990");

    assertThat(response.getBody().toString()).isEqualTo("[]");
  }

  @Test
  @DisplayName("findAddress should throw exception when people not found")
  void testFindAddressShouldThrowExceptionWhenPeopleNotFound() {
    try {
      var response = addressServiceImplementation.findAddress("Anderson", "20/12/1990");
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Anderson and Birth Date: 20/12/1990-> Not found"
      );
    }
  }

  @Test
  @DisplayName("makeAddress Should return status code 201")
  void testMakeAddressShouldReturnStatusCode201() {
    makeTempPeople();

    var addressDTO = new AddressDTO("Rua 1", "1", "São Paulo", "04001-000");
    var response = addressServiceImplementation.makeAddress("Anderson", "20/12/1990", addressDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(201);
  }

  @Test
  @DisplayName("makeAddress Should throw exception when people not found")
  void testMakeAddressShouldThrowExceptionWhenPeopleNotFound() {
    try {
      var addressDTO = new AddressDTO("Rua 1", "1", "São Paulo", "04001-000");
      var response = addressServiceImplementation.makeAddress("Anderson", "20/12/1990", addressDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Anderson and Birth Date: 20/12/1990-> Not found"
      );
    }
  }

  @Test
  @DisplayName("deleteAddress Should return status code 200")
  void testDeleteAddressShouldReturnStatusCode200() {
    makeTempPeople();

    var addressDTO = new AddressDTO("Rua 1", "1", "São Paulo", "04001-000");
    var response = addressServiceImplementation.deleteAddress("Anderson", "20/12/1990", addressDTO);

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }

  @Test
  @DisplayName("deleteAddress Should throw exception when people not found")
  void testDeleteAddressShouldThrowExceptionWhenPeopleNotFound() {
    try {
      var addressDTO = new AddressDTO("Rua 1", "1", "São Paulo", "04001-000");
      var response = addressServiceImplementation.deleteAddress("Anderson", "20/12/1990",
          addressDTO);
    } catch (Exception e) {
      assertThat(e.getMessage()).isEqualTo(
          "People with name: Anderson and Birth Date: 20/12/1990-> Not found"
      );
    }
  }

  void makeTempPeople() {
    var birthDate = LocalDate.parse("20/12/1990", DateTimeFormatter.ofPattern("d/MM/yyyy"));
    var peopleEntity = new PeopleEntity("Anderson", birthDate);

    peopleRepository.save(peopleEntity);
  }


}