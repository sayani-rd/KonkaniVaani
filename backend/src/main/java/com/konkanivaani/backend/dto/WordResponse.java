package com.konkanivaani.backend.dto;

import com.konkanivaani.backend.model.Region;
import com.konkanivaani.backend.model.Word;

import java.util.List;

public record WordResponse(String id, String konkaniWord, String romanized, String meaning,
                           String partOfSpeech, String status,
                           List<VariationResponse> variations, List<String> missingRegions) {

    /** Only APPROVED variations are exposed to the public. */
    public static WordResponse from(Word w) {
        List<VariationResponse> vars = w.getVariations().stream()
                .filter(v -> v.getStatus().isPublic())
                .map(VariationResponse::from)
                .toList();                                   // immutable list (Java 16+)
        List<String> missing = w.getMissingRegions().stream()
                .map(Region::getDisplayName)
                .toList();
        return new WordResponse(w.getId(), w.getKonkaniWord(), w.getRomanized(), w.getMeaning(),
                w.getPartOfSpeech().name(), w.getStatus().name(), vars, missing);
    }
}