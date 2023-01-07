package dev.anderson.peopleapi.domain.entities;

import dev.anderson.peopleapi.domain.DTO.PeopleInputDTO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PeopleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDate birthDate;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<AddressEntity> addresses;

  public PeopleEntity() {
  }

  public PeopleEntity(String name, LocalDate birthDate, List<AddressEntity> addresses) {
    this.name = name;
    this.birthDate = birthDate;
    this.addresses = addresses;
  }

  public PeopleEntity(String name, LocalDate birthDate) {
    this.name = name;
    this.birthDate = birthDate;
    this.addresses = new ArrayList<>();
  }

  static public PeopleEntity of(String name, LocalDate birthDate) {
    return new PeopleEntity(name, birthDate);
  }

  public String getName() {
    return name;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public List<AddressEntity> getAddresses() {
    return addresses;
  }

  public void updateNameAndDate(PeopleInputDTO peopleInputDTO) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    if (!peopleInputDTO.newName().isEmpty()) {
      this.name = peopleInputDTO.newName();
    }
    if (!peopleInputDTO.newBirthDate().isEmpty()) {
      this.birthDate = LocalDate.parse(peopleInputDTO.newBirthDate(), formatter);
    }
  }

  public void updateAddress(AddressEntity addressEntity) {
    this.addresses.add(addressEntity);
  }

  public void deleteAddress(AddressEntity addressEntity) {
    this.addresses.remove(addressEntity);
  }

}
