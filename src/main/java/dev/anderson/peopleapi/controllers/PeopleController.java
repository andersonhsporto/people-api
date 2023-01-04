package dev.anderson.peopleapi.controllers;

import dev.anderson.peopleapi.service.implementation.PeopleServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/people")
public class PeopleController {

  private final PeopleServiceImplementation peopleServiceImplementation;


  public PeopleController(PeopleServiceImplementation peopleServiceImplementation) {
    this.peopleServiceImplementation = peopleServiceImplementation;
  }

  @GetMapping
  public ResponseEntity<?> listAll() {
    return ResponseEntity.ok(peopleServiceImplementation.listAll());
  }




}
