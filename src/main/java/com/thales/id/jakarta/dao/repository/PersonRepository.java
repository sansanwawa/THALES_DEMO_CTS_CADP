package com.thales.id.jakarta.dao.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.thales.id.jakarta.entities.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Author:
 * sandy.haryono@thalesgroup.com
 */

public interface PersonRepository extends CrudRepository<Person, Long>{

    @Query("from Person")
    public List<Person> getAllPerson(Pageable pageable);


}
