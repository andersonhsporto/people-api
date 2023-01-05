package dev.anderson.peopleapi.controllers;

import dev.anderson.peopleapi.domain.DTO.PeopleInputDTO;
import dev.anderson.peopleapi.service.implementation.PeopleServiceImplementation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/people")
public class PeopleController {

  private final PeopleServiceImplementation peopleServiceImplementation;

  public PeopleController(PeopleServiceImplementation peopleServiceImplementation) {
    this.peopleServiceImplementation = peopleServiceImplementation;
  }

  @GetMapping("/all")
  public ResponseEntity<?> listAll(
      @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
      @RequestParam(value = "size", required = false, defaultValue = "4") Integer size
  ) {
    return peopleServiceImplementation.listAll(page, size);
  }

  @PostMapping
  public ResponseEntity<?> makePeople(
      @RequestParam(value = "name", required = true) String name,
      @RequestParam(value = "birthDate", required = true) String birthDate
  ) {
    return peopleServiceImplementation.makePeople(name, birthDate);
  }

  @PutMapping
  public ResponseEntity<?> replacePeople(
      @RequestBody(required = true) PeopleInputDTO peopleInputDTO) {
    return peopleServiceImplementation.replacePeople(peopleInputDTO);
  }

  @PatchMapping
  public ResponseEntity<?> updatePeople(
      @RequestBody(required = true) PeopleInputDTO peopleInputDTO) {
    return peopleServiceImplementation.updatePeople(peopleInputDTO);
  }

}
