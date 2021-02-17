package com.example.springshadow.database.fdms.repository;

import com.example.springshadow.model.database.fdms.InfoFdms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface InfoFdmsRepository extends JpaRepository<InfoFdms, Long> {

}
