package dev.anderson.peopleapi.service.implementation;

import dev.anderson.peopleapi.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplementation implements AddressService {

  @Override
  public ResponseEntity<?> listAll() {
    return null;
  }
}
