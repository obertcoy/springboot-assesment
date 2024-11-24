package com.lacak.backend.assesment.models.dtos.suggestion;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuggestionRequestDto {

    @NotBlank(message = "Query cannot be empty")
    private String query;

    @DecimalMin(value = "-90.0", message = "Latitude must be greater than or equal to -90")
    @DecimalMax(value = "90.0", message = "Latitude must be less than or equal to 90")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "Longitude must be greater than or equal to -180")
    @DecimalMax(value = "180.0", message = "Longitude must be less than or equal to 180")
    private Double longitude;

    @AssertTrue(message = "Both latitude and longitude must be provided if one is present")
    private boolean isCoordinatesValid() {
        return (latitude == null && longitude == null) || (latitude != null && longitude != null);
    }

}
