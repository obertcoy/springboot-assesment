package com.lacak.backend.assessment.models.dtos.suggestion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionResponseDto implements Comparable<SuggestionResponseDto> {

    private String name;
    private Double latitude;
    private Double longitude;
    private Double score;

    @Override
    public int compareTo(SuggestionResponseDto other) {
        // Default descending order
        return Double.compare(other.getScore(), this.getScore());
    }

}
