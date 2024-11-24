package com.lacak.backend.assesment.services;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.lacak.backend.assesment.enums.ScoreTypeEnum;
import com.lacak.backend.assesment.helpers.SuggestionScoringHelper;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionRequestDto;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionResponseDto;
import com.lacak.backend.assesment.models.entities.suggestion.Suggestion;
import com.lacak.backend.assesment.repositories.SuggestionRepository;

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

        List<Suggestion> suggestionEntities = this.suggestionRepository.findByNameContaining(requestDto.getQuery());

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
