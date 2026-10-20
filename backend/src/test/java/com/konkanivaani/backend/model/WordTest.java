package com.konkanivaani.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordTest {

    @Test
    void missingRegionsShrinksWhenApprovedVariationAdded() {
        Word w = new Word("उदक", "udak", "water", PartOfSpeech.NOUN);
        assertEquals(Region.values().length, w.getMissingRegions().size());

        DialectVariation v = new DialectVariation(Region.GOA, "udak", "user1");
        v.setStatus(SubmissionStatus.APPROVED);
        w.addVariation(v);

        assertFalse(w.getMissingRegions().contains(Region.GOA));
    }
}