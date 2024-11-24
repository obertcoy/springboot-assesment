package com.lacak.backend.assesment.models.entities.suggestion;

import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "locations")
public class Suggestion {

    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lat")
    private Double latitude;

    @Column(name = "long")
    private Double longitude;

    @Column(name = "population")
    private Long population;

    public SuggestionResponseDto mapToResponseDto() {
        return new SuggestionResponseDto(this.name, this.latitude, this.longitude, Double.valueOf(0.0));
    }
}
