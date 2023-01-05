package dev.anderson.peopleapi.repositories;

import dev.anderson.peopleapi.domain.entities.PeopleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends PagingAndSortingRepository<PeopleEntity, Long> {

}