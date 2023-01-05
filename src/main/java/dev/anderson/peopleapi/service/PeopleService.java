package dev.anderson.peopleapi.service;

import org.springframework.http.ResponseEntity;

public interface PeopleService {

  ResponseEntity<?> listAll(Integer page, Integer size);

  ResponseEntity<?> makePeople(String name, String birthDate);

}
