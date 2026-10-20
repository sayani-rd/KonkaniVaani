package com.konkanivaani.backend.dto;

import com.konkanivaani.backend.model.DialectVariation;

public record VariationResponse(String id, String region, String regionName,
                                String writtenPronunciation, String audioUrl) {

    /** Compact conversion from the model class. */
    public static VariationResponse from(DialectVariation v) {
        return new VariationResponse(v.getId(), v.getRegion().name(),
                v.getRegion().getDisplayName(), v.getWrittenPronunciation(), v.getAudioUrl());
    }
}