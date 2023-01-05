package dev.anderson.peopleapi.domain.DTO;

import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;

public record PeopleDTO(
    String name,
    String birthDate,
    List<AddressDTO> addressDTO) {

  public static PeopleDTO of(PeopleEntity peopleEntity) {
    return new PeopleDTO(
        peopleEntity.getName(),
        peopleEntity.getBirthDate().toString(),
        AddressDTO.fromEntityList(peopleEntity.getAddresses())
    );
  }

  public static List<PeopleDTO> fromPage(Page<PeopleEntity> peopleEntityList) {
    List<PeopleDTO> peopleDTOList = new ArrayList<>();

    for (PeopleEntity peopleEntity : peopleEntityList) {
      peopleDTOList.add(of(peopleEntity));
    }
    return peopleDTOList;
  }

}
