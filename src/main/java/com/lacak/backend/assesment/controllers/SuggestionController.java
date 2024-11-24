package com.lacak.backend.assesment.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lacak.backend.assesment.models.dtos.api.APIResponse;
import com.lacak.backend.assesment.models.dtos.api.ApiResponse;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionRequestDto;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionResponseDto;
import com.lacak.backend.assesment.services.SuggestionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/suggestion")
@Slf4j
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<List<SuggestionResponseDto>>> getSuggestion(
            @Valid SuggestionRequestDto requestDto) {

        List<SuggestionResponseDto> suggestionResponseDtos = this.suggestionService.getSuggestion(requestDto);
        return ResponseEntity.ok(ApiResponse.success(suggestionResponseDtos));
    }

}
