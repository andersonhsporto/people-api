package dev.anderson.peopleapi.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

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

  public AddressEntity(
      String publicPlace,
      String number,
      String city,
      String cep,
      PeopleEntity people) {
    this.publicPlace = publicPlace;
    this.number = number;
    this.city = city;
    this.cep = cep;
    this.people = people;
  }

  public Long getId() {
    return id;
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

  public PeopleEntity getPeople() {
    return people;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddressEntity that = (AddressEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(publicPlace,
        that.publicPlace) && Objects.equals(number, that.number)
        && Objects.equals(city, that.city) && Objects.equals(cep, that.cep);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, publicPlace, number, city, cep);
  }
}
