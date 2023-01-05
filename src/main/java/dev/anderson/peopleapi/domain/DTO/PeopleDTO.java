package dev.anderson.peopleapi.domain.DTO;

public record PeopleDTO(String name, String birthDate) {

  public static PeopleDTO of(String name, String birthDate) {
    return new PeopleDTO(name, birthDate);
  }
}
