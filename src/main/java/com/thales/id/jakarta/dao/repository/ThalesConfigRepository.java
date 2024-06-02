package com.thales.id.jakarta.dao.repository;

import com.thales.id.jakarta.entities.ThalesConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Author:
 * sandy.haryono@thalesgroup.com
 */

public interface ThalesConfigRepository extends CrudRepository<ThalesConfig, String>{

    @Query("from ThalesConfig where configName=:configName")
    public Optional<ThalesConfig> getConfigValue(String configName);


}
