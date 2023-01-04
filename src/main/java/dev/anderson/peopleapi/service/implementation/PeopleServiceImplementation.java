package dev.anderson.peopleapi.service.implementation;

import dev.anderson.peopleapi.service.PeopleService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImplementation implements PeopleService {

  @Override
  public ResponseEntity<?> listAll() {
    return new ResponseEntity<>("Hello World", null, 200);
  }
}
