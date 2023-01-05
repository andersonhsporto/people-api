package dev.anderson.peopleapi.repositories;

import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import java.time.LocalDate;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends PagingAndSortingRepository<PeopleEntity, Long> {

  boolean existsByNameAndBirthDate(String name, LocalDate birthDate);
}