package dev.anderson.peopleapi.service.implementation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

  @Test
  @DisplayName("Should return status code 200")
  void testShouldReturnStatusCode200() {
    ResponseEntity<?> response = addressServiceImplementation.listAll();

    assertThat(response.getStatusCode().value()).isEqualTo(200);
  }
}