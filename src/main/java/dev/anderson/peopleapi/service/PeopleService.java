package dev.anderson.peopleapi.service;

import dev.anderson.peopleapi.domain.DTO.PeopleInputDTO;
import org.springframework.http.ResponseEntity;

public interface PeopleService {

  ResponseEntity<?> listAll(Integer page, Integer size);

  ResponseEntity<?> makePeople(String name, String birthDate);

  ResponseEntity<?> replacePeople(PeopleInputDTO peopleInputDTO);

  ResponseEntity<?> updatePeople(PeopleInputDTO peopleInputDTO);

  ResponseEntity<?> deletePeople(String name, String birthDate);
}
