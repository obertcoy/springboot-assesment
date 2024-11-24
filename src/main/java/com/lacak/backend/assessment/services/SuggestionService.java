package com.lacak.backend.assessment.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lacak.backend.assessment.helpers.SuggestionScoringHelper;
import com.lacak.backend.assessment.models.dtos.suggestion.SuggestionRequestDto;
import com.lacak.backend.assessment.models.dtos.suggestion.SuggestionResponseDto;
import com.lacak.backend.assessment.models.entities.suggestion.Suggestion;
import com.lacak.backend.assessment.repositories.SuggestionRepository;

@Service
public class SuggestionService {

    private final SuggestionRepository suggestionRepository;
    private final SuggestionScoringHelper suggestionScoringHelper;

    public SuggestionService(SuggestionRepository suggestionRepository,
            SuggestionScoringHelper suggestionScoringHelper) {
        this.suggestionRepository = suggestionRepository;
        this.suggestionScoringHelper = suggestionScoringHelper;
    }

    public List<SuggestionResponseDto> getSuggestion(SuggestionRequestDto requestDto) {

        // Using database to make the search faster and scalable for later
        // Assumes the query input is accurate and free of typos.
        // If typo is taken into account, consider using findAll, then the calculateScore function will handle fuzzy matching.
        List<Suggestion> suggestionEntities = this.suggestionRepository.findByNameContaining(requestDto.getQ());

        List<SuggestionResponseDto> suggestionResponseDtos = suggestionEntities
                .stream().map(entity -> {
                    SuggestionResponseDto responseDto = entity.mapToResponseDto();
                    Double score = suggestionScoringHelper.calculateScore(requestDto, entity);
                    responseDto.setScore(score);
                    return responseDto;
                })
                .sorted()
                .toList();

        return suggestionResponseDtos;
    }

    // Alternative to empty query input ** NOT USED **
    public List<SuggestionResponseDto> getTop10ByPopulation() {
        List<Suggestion> suggestionEntities = this.suggestionRepository.findTop10ByPopulation();

        List<SuggestionResponseDto> suggestionResponseDtos = suggestionEntities
                .stream().map(entity -> {
                    SuggestionResponseDto responseDto = entity.mapToResponseDto();
                    return responseDto;
                })
                .sorted()
                .toList();
        ;
        return suggestionResponseDtos;

    }

}
