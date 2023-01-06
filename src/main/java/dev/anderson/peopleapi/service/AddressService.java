package dev.anderson.peopleapi.service;

import dev.anderson.peopleapi.domain.DTO.AddressDTO;
import org.springframework.http.ResponseEntity;

public interface AddressService {

  ResponseEntity<?> listAll();

  ResponseEntity<?> findAddress(String name, String birthDate);

  ResponseEntity<?> makeAddress(String name, String birthDate, AddressDTO addressDTO);
}
