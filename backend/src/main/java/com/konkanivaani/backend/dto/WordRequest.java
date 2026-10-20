package com.konkanivaani.backend.dto;

import com.konkanivaani.backend.model.PartOfSpeech;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Incoming data for creating/updating a word. Annotations validate it automatically. */
public record WordRequest(
        @NotBlank(message = "konkaniWord is required") String konkaniWord,
        @NotBlank(message = "romanized is required") String romanized,
        @NotBlank(message = "meaning is required") String meaning,
        @NotNull(message = "partOfSpeech is required") PartOfSpeech partOfSpeech
) { }