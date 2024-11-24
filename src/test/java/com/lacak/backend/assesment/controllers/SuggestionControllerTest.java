package com.lacak.backend.assesment.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionRequestDto;
import com.lacak.backend.assesment.models.dtos.suggestion.SuggestionResponseDto;
import com.lacak.backend.assesment.services.SuggestionService;

@WebMvcTest(SuggestionController.class)
class SuggestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SuggestionService suggestionService;

    private List<SuggestionResponseDto> mockResponses;

    @BeforeEach
    void setUp() {

        SuggestionResponseDto response1 = new SuggestionResponseDto("Londontowne, MD, USA", 38.93345, -76.54941, 0.3);
        SuggestionResponseDto response2 = new SuggestionResponseDto("London, ON, Canada", 42.9849, -81.2453, 0.9);
        SuggestionResponseDto response3 = new SuggestionResponseDto("London, KY, USA", 37.12898, -84.08326, 0.5);

        mockResponses = Arrays.asList(response1, response2, response3);
    }

    @Test
    void getSuggestion_WithValidQuery_ReturnsSuccess() throws Exception {
        when(suggestionService.getSuggestion(any(SuggestionRequestDto.class)))
                .thenReturn(mockResponses.stream()
                        .sorted()
                        .collect(Collectors.toList()));

        mockMvc.perform(get("/suggestions")
                .param("q", "London"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void getSuggestion_WithPartialQuery_ReturnsSuccess() throws Exception {
        when(suggestionService.getSuggestion(any(SuggestionRequestDto.class)))
                .thenReturn(mockResponses.stream()
                        .sorted()
                        .collect(Collectors.toList()));

        mockMvc.perform(get("/suggestions")
                .param("q", "Londo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[2].name").value("Londontowne, MD, USA"));

    }

    @Test
    void getSuggestion_WithEmptyQuery_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", ""))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void getSuggestion_WithPartialQueryAndValidCoordinates_ReturnsSuccess() throws Exception {
        when(suggestionService.getSuggestion(any(SuggestionRequestDto.class)))
                .thenReturn(mockResponses.stream()
                        .sorted()
                        .collect(Collectors.toList()));

        mockMvc.perform(get("/suggestions")
                .param("q", "Londo")
                .param("latitude", "43.70011")
                .param("longitude", "-79.4163"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].name").value("London, ON, Canada"))
                .andExpect(jsonPath("$.data[1].name").value("London, KY, USA"))
                .andExpect(jsonPath("$.data[2].name").value("Londontowne, MD, USA"));
    }

    @Test
    void getSuggestion_WithInvalidLatitude_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "London")
                .param("latitude", "91.0")
                .param("longitude", "-0.1278"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void getSuggestion_WithMissingLongitude_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "London")
                .param("latitude", "51.5074"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void getSuggestion_WithInvalidQuery_ReturnsSuccess() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "SomeRandomCityInTheMiddleOfNowhere"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void getSuggestion_WithInvalidLatitudeString_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "London")
                .param("latitude", "invalid")
                .param("longitude", "-0.1278"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void getSuggestion_WithInvalidLongitudeString_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "London")
                .param("latitude", "51.5074")
                .param("longitude", "invalid"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));

    }

    @Test
    void getSuggestion_WithBothInvalidLatitudeAndLongitudeStrings_ReturnsBadRequest() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "London")
                .param("latitude", "not-a-number")
                .param("longitude", "not-a-number"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

}