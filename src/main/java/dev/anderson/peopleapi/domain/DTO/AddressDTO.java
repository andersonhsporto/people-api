package dev.anderson.peopleapi.domain.DTO;

import dev.anderson.peopleapi.domain.entities.AddressEntity;
import java.util.ArrayList;
import java.util.List;

public record AddressDTO(
    String publicPlace,
    String number,
    String city,
    String cep
) {

  public static AddressDTO of(
      AddressEntity addressEntity) {
    return new AddressDTO(
        addressEntity.getPublicPlace(),
        addressEntity.getNumber(),
        addressEntity.getCity(),
        addressEntity.getCep()
    );
  }

  public static List<AddressDTO> fromEntityList(List<AddressEntity> addressEntitiesList) {
    List<AddressDTO> addressDTOSList = new ArrayList<>();

    for (AddressEntity addressEntity : addressEntitiesList) {
      addressDTOSList.add(of(addressEntity));
    }
    return addressDTOSList;
  }


}
