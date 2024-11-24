package com.lacak.backend.assessment.models.entities.suggestion;

import com.lacak.backend.assessment.models.dtos.suggestion.SuggestionResponseDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
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

    @Column(name = "country")
    private String country;

    @Column(name = "admin1")
    private String admin1;

    public SuggestionResponseDto mapToResponseDto() {
        String uniqueName = String.format("%s, %s, %s", this.name, this.admin1, this.country);
        return new SuggestionResponseDto(uniqueName, this.latitude, this.longitude, Double.valueOf(0.0));
    }
}
