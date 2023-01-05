package dev.anderson.peopleapi.service.implementation;

import dev.anderson.peopleapi.domain.DTO.PeopleDTO;
import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.exceptions.UserExistsException;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import dev.anderson.peopleapi.service.PeopleService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PeopleServiceImplementation implements PeopleService {

  private final PeopleRepository peopleRepository;

  public PeopleServiceImplementation(PeopleRepository peopleRepository) {
    this.peopleRepository = peopleRepository;
  }

  @Override
  public ResponseEntity<?> listAll() {
    List<PeopleEntity> peopleList = peopleRepository.findAll();

    return new ResponseEntity<>(PeopleDTO.fromList(peopleList), null, 200);
  }

//  @Override
//  public ResponseEntity<?> listAll() {
//    PeopleEntity peopleEntity = peopleRepository.getReferenceById(1L);
//
//    return new ResponseEntity<>(PeopleDTO.of(peopleEntity.getName(), "TESTE"),
//        null, 200);
//  }

  @Override
  public ResponseEntity<?> makePeople(String name, String birthDate) {
    LocalDate entityDate = parseDate(birthDate);

    if (peopleRepository.existsByNameAndBirthDate(name, entityDate)) {
      throw new UserExistsException(
          "User: " + name + " With Birth Date: " + birthDate + "-> Already Exist"
      );
    } else {
      peopleRepository.save(PeopleEntity.of(name, entityDate));
    }
    return new ResponseEntity<>(null, null, 200);
  }

  LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    return LocalDate.parse(date, formatter);
  }
}
