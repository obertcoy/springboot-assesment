package com.lacak.backend.assesment.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lacak.backend.assesment.models.entities.suggestion.Suggestion;

@Repository
public interface SuggestionRepository extends CrudRepository<Suggestion, Long> {

    List<Suggestion> findByNameContaining(String query);

    @Query("SELECT * FROM locations ORDER BY population DESC")
    List<Suggestion> findTop10ByPopulation();
}
