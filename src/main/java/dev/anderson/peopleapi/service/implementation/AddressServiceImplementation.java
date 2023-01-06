package dev.anderson.peopleapi.service.implementation;

import dev.anderson.peopleapi.domain.DTO.AddressDTO;
import dev.anderson.peopleapi.domain.entities.AddressEntity;
import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import dev.anderson.peopleapi.exceptions.UserNotFoundException;
import dev.anderson.peopleapi.repositories.AddressRepository;
import dev.anderson.peopleapi.repositories.PeopleRepository;
import dev.anderson.peopleapi.service.AddressService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImplementation implements AddressService {

  private final PeopleRepository peopleRepository;

  private final AddressRepository addressRepository;

  public AddressServiceImplementation(
      PeopleRepository peopleRepository,
      AddressRepository addressRepository
  ) {
    this.peopleRepository = peopleRepository;
    this.addressRepository = addressRepository;
  }

  @Override
  public ResponseEntity<?> listAll() {
    return ResponseEntity.ok(AddressDTO.fromEntityList(addressRepository.findAll()));
  }

  @Override
  public ResponseEntity<?> findAddress(String name, String birthDate) {
    PeopleEntity peopleEntity = findEntity(name, birthDate);

    List<AddressEntity> addressList = peopleEntity.getAddresses();
    return new ResponseEntity<>(AddressDTO.fromEntityList(addressList), null, 200);
  }

  @Override
  public ResponseEntity<?> makeAddress(String name, String birthDate, AddressDTO addressDTO) {
    PeopleEntity peopleEntity = findEntity(name, birthDate);

    peopleEntity.updateAddress(AddressEntity.fromDTO(addressDTO));
    peopleRepository.save(peopleEntity);
    return new ResponseEntity<>(null, null, 201);
  }

  private PeopleEntity findEntity(String name, String birthDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    return peopleRepository
        .findByNameAndBirthDate(name, LocalDate.parse(birthDate, formatter))
        .orElseThrow(() -> new UserNotFoundException(
            "People with name: " + name + " and Birth Date: "
                + birthDate + "-> Not found"
        ));
  }


}
