package dev.anderson.peopleapi.repositories;

import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleEntity, Long> {

  boolean existsByNameAndBirthDate(String name, LocalDate birthDate);

  Optional<PeopleEntity> findByNameAndBirthDate(String name, LocalDate birthDate);

  Page<PeopleEntity> findAll(Pageable pageable);


}