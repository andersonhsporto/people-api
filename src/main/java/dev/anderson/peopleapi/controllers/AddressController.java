package dev.anderson.peopleapi.controllers;

import dev.anderson.peopleapi.service.implementation.AddressServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/address")
public class AddressController {

  private final AddressServiceImplementation addressServiceImplementation;

  public AddressController(AddressServiceImplementation addressServiceImplementation) {
    this.addressServiceImplementation = addressServiceImplementation;
  }

  @GetMapping
  ResponseEntity<?> listAll() {
    return addressServiceImplementation.listAll();
  }
}
