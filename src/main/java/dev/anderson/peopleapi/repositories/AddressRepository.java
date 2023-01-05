package dev.anderson.peopleapi.repositories;

import dev.anderson.peopleapi.domain.entities.AddressEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<AddressEntity, Long> {

}
