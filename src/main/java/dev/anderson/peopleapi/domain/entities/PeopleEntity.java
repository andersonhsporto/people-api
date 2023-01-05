package dev.anderson.peopleapi.domain.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class PeopleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private LocalDate birthDate;

  @OneToMany(cascade = CascadeType.ALL)
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
  }

  static public PeopleEntity of(String name, LocalDate birthDate) {
    return new PeopleEntity(name, birthDate);
  }

  public Long getId() {
    return id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PeopleEntity that = (PeopleEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(birthDate, that.birthDate) && Objects.equals(addresses,
        that.addresses);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, birthDate, addresses);
  }
}
