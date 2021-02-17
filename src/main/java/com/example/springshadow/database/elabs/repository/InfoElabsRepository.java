package com.example.springshadow.database.elabs.repository;

import java.util.List;

import com.example.springshadow.model.database.elabs.InfoElabs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface InfoElabsRepository extends JpaRepository<InfoElabs, Long> {
    @Query(nativeQuery = true, name = "InfoElabs.findWithLimit", value = "select * from InfoElabs")
    List<InfoElabs> findwithLimit();
}
