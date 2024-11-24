package com.lacak.backend.assesment.helpers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lacak.backend.assesment.enums.ScoreTypeEnum;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionRequestDto;
import com.lacak.backend.assesment.models.entities.suggestion.Suggestion;
import com.lacak.backend.assesment.utils.GeoDistanceUtils;
import com.lacak.backend.assesment.utils.StringMatchingUtils;

import lombok.Getter;

@Getter
@Component
public class SuggestionScoringHelper {
    private final double matchingWeight;
    private final double geoDistanceWeight;

    public SuggestionScoringHelper(
            @Value("${scoring.matchingWeight}") double matchingWeight,
            @Value("${scoring.geoDistanceWeight}") double geoDistanceWeight) {
        this.matchingWeight = matchingWeight;
        this.geoDistanceWeight = geoDistanceWeight;
    }

    public double calculateScore(SuggestionRequestDto requestDto, Suggestion entity) {

        ScoreTypeEnum scoreTypeEnum = ScoreTypeEnum.NORMALIZED;

        double normalizedMatchingDistance = StringMatchingUtils.calculateLevenshteinDistance(requestDto.getQ(),
                entity.getName(), scoreTypeEnum);

        double matchingScore = this.matchingWeight * normalizedMatchingDistance;
        double geoDistanceScore = 0.0;

        if (requestDto.getLatitude() != null && requestDto.getLongitude() != null) {
            double normalizedGeoDistance = GeoDistanceUtils.calculateHaversineDistance(
                    requestDto.getLatitude(), requestDto.getLongitude(), entity.getLatitude(), entity.getLongitude(),
                    scoreTypeEnum);

            geoDistanceScore = this.geoDistanceWeight * normalizedGeoDistance;
        }

        return matchingScore + geoDistanceScore;
    }

}
