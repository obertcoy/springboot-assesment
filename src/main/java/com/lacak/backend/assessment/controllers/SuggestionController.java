package com.lacak.backend.assessment.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lacak.backend.assessment.models.dtos.api.ApiResponse;
import com.lacak.backend.assessment.models.dtos.suggestion.SuggestionRequestDto;
import com.lacak.backend.assessment.models.dtos.suggestion.SuggestionResponseDto;
import com.lacak.backend.assessment.services.SuggestionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/suggestions")
@Slf4j
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SuggestionResponseDto>>> getSuggestion(
            @Valid SuggestionRequestDto requestDto) {

        List<SuggestionResponseDto> suggestionResponseDtos = this.suggestionService.getSuggestion(requestDto);
        return ResponseEntity.ok(ApiResponse.success(suggestionResponseDtos));
    }

}
