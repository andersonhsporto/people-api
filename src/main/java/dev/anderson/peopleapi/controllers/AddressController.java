package dev.anderson.peopleapi.controllers;

import dev.anderson.peopleapi.domain.DTO.AddressDTO;
import dev.anderson.peopleapi.service.implementation.AddressServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/address")
public class AddressController {

  private final AddressServiceImplementation addressServiceImplementation;

  public AddressController(AddressServiceImplementation addressServiceImplementation) {
    this.addressServiceImplementation = addressServiceImplementation;
  }

  @GetMapping("/all")
  ResponseEntity<?> listAll() {
    return addressServiceImplementation.listAll();
  }

  @GetMapping
  ResponseEntity<?> findAddress(
      @RequestParam(value = "name", required = true) String name,
      @RequestParam(value = "birthDate", required = true) String birthDate) {
    return addressServiceImplementation.findAddress(name, birthDate);
  }

  @PostMapping
  ResponseEntity<?> makeAddress(
      @RequestParam(value = "name", required = true) String name,
      @RequestParam(value = "birthDate", required = true) String birthDate,
      @RequestBody(required = true) AddressDTO addressDTO
  ) {
    return addressServiceImplementation.makeAddress(name, birthDate, addressDTO);
  }

}
