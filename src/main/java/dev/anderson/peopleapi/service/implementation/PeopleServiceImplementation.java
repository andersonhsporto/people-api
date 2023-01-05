package dev.anderson.peopleapi.service.implementation;

import dev.anderson.peopleapi.domain.DTO.PeopleDTO;
import dev.anderson.peopleapi.domain.DTO.PeopleInputDTO;
import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.exceptions.UserExistsException;
import dev.anderson.peopleapi.exceptions.UserNotFoundException;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import dev.anderson.peopleapi.service.PeopleService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeopleServiceImplementation implements PeopleService {

  private final PeopleRepository peopleRepository;

  public PeopleServiceImplementation(PeopleRepository peopleRepository) {
    this.peopleRepository = peopleRepository;
  }

  @Override
  public ResponseEntity<?> listAll(Integer page, Integer size) {
    Pageable pageRequest = PageRequest.of(page, size, Sort.by("name"));
    Page<PeopleEntity> peoplePages = peopleRepository.findAll(pageRequest);

    return new ResponseEntity<>(
        PeopleDTO.fromPage(peoplePages),
        getTotalElements(peoplePages),
        200);
  }

  @Override
  public ResponseEntity<?> findPeople(String name, String birthDate) {
    LocalDate entityDate = parseDate(birthDate);

    if (!peopleRepository.existsByNameAndBirthDate(name, entityDate)) {
      throw new UserNotFoundException(
          "People with name: " + name + " and Birth Date: "
              + birthDate + "-> Not found"
      );
    }

    Optional<PeopleEntity> peopleEntity
        = peopleRepository.findByNameAndBirthDate(name, entityDate);
    return new ResponseEntity<>(PeopleDTO.of(peopleEntity.get()), null, 200);
  }

  @Override
  public ResponseEntity<?> makePeople(String name, String birthDate) {
    LocalDate entityDate = parseDate(birthDate);

    if (peopleRepository.existsByNameAndBirthDate(name, entityDate)) {
      throw new UserExistsException(
          "People with name: " + name + " and Birth Date: " + birthDate + "-> Already Exist"
      );
    } else {
      peopleRepository.save(PeopleEntity.of(name, entityDate));
    }
    return new ResponseEntity<>(null, null, 200);
  }

  @Override
  public ResponseEntity<?> replacePeople(PeopleInputDTO peopleInputDTO) {
    LocalDate entityDate = parseDate(peopleInputDTO.birthDate());

    if (dtoExists(peopleInputDTO)) {
      Optional<PeopleEntity> peopleEntity
          = peopleRepository.findByNameAndBirthDate(peopleInputDTO.name(), entityDate);

      peopleEntity.get().updateNameAndDate(peopleInputDTO);
      peopleRepository.save(peopleEntity.get());
    }
    return new ResponseEntity<>(null, null, 200);
  }

  @Override
  public ResponseEntity<?> updatePeople(PeopleInputDTO peopleInputDTO) {
    LocalDate entityDate = parseDate(peopleInputDTO.birthDate());

    if (!peopleRepository.existsByNameAndBirthDate(peopleInputDTO.name(), entityDate)) {
      throw new UserNotFoundException(
          "People with name: " + peopleInputDTO.name() + " and Birth Date: "
              + peopleInputDTO.birthDate() + "-> Not found"
      );
    }

    Optional<PeopleEntity> peopleEntity
        = peopleRepository.findByNameAndBirthDate(peopleInputDTO.name(), entityDate);

    peopleEntity.get().updateNameAndDate(peopleInputDTO);
    peopleRepository.save(peopleEntity.get());
    return new ResponseEntity<>(null, null, 200);
  }

  @Override
  @Transactional
  public ResponseEntity<?> deletePeople(String name, String birthDate) {
    LocalDate entityDate = parseDate(birthDate);

    if (!peopleRepository.existsByNameAndBirthDate(name, entityDate)) {
      throw new UserNotFoundException(
          "People with name: " + name + " and Birth Date: " + birthDate + "-> Not found"
      );
    }

    peopleRepository.deleteByNameAndBirthDate(name, entityDate);
    return new ResponseEntity<>(null, null, 200);
  }

  private boolean dtoExists(PeopleInputDTO peopleInputDTO) {
    LocalDate oldDate = parseDate(peopleInputDTO.birthDate());
    LocalDate newDate = parseDate(peopleInputDTO.newBirthDate());

    if (!peopleRepository.existsByNameAndBirthDate(peopleInputDTO.name(), oldDate)) {
      throw new UserNotFoundException(
          "People with name: " + peopleInputDTO.name() + " and Birth Date: "
              + peopleInputDTO.birthDate() + "-> Not found"
      );
    } else if (peopleRepository.existsByNameAndBirthDate(peopleInputDTO.newName(), newDate)) {
      throw new UserExistsException(
          "People with name: " + peopleInputDTO.newName() + " and Birth Date: "
              + peopleInputDTO.newBirthDate() + "-> Already Exist"
      );
    } else {
      return true;
    }
  }

  private HttpHeaders getTotalElements(Page<PeopleEntity> peopleEntity) {
    HttpHeaders headers = new HttpHeaders();

    headers.add("Access-Control-Expose-Headers", "X-Total-Count");
    headers.add("x-total-count", String.valueOf(peopleEntity.getTotalElements()));
    return headers;
  }

  private LocalDate parseDate(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    return LocalDate.parse(date, formatter);
  }
}
