package dev.anderson.peopleapi.service;

import io.swagger.models.Response;
import org.springframework.http.ResponseEntity;

public interface AddressService {

  ResponseEntity<?> listAll();
}
