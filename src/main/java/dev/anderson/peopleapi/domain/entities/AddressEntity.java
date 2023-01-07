package dev.anderson.peopleapi.domain.entities;

import dev.anderson.peopleapi.domain.DTO.AddressDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class AddressEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String publicPlace;

  private String number;

  private String city;

  private String cep;

  @ManyToOne
  private PeopleEntity people;

  public AddressEntity() {
  }

  public AddressEntity(String publicPlace, String number, String city, String cep) {
    this.publicPlace = publicPlace;
    this.number = number;
    this.city = city;
    this.cep = cep;
  }

  public String getPublicPlace() {
    return publicPlace;
  }

  public String getNumber() {
    return number;
  }

  public String getCity() {
    return city;
  }

  public String getCep() {
    return cep;
  }


  public static AddressEntity fromDTO(AddressDTO addressDTO) {
    return new AddressEntity(
        addressDTO.publicPlace(),
        addressDTO.number(),
        addressDTO.city(),
        addressDTO.cep()
    );
  }

}
