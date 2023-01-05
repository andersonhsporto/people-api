package dev.anderson.peopleapi.service;

import org.springframework.http.ResponseEntity;

public interface PeopleService {

  ResponseEntity<?> listAll();

  ResponseEntity<?> makePeople(String name, String birthDate);

}
