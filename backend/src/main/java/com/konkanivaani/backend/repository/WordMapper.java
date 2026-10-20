package com.konkanivaani.backend.repository;

import com.google.cloud.Timestamp;
import com.konkanivaani.backend.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class: final + private constructor + static methods only.
 * We map by hand (Map <-> object) because Firestore cannot store java.time.Instant directly.
 */
public final class WordMapper {

    private WordMapper() { }   // prevents instantiation

    public static Map<String, Object> toMap(Word w) {
        Map<String, Object> m = new HashMap<>();
        m.put("konkaniWord", w.getKonkaniWord());
        m.put("romanized", w.getRomanized());
        m.put("meaning", w.getMeaning());
        m.put("partOfSpeech", w.getPartOfSpeech().name());   // enum -> String
        m.put("status", w.getStatus().name());
        m.put("createdAt", Date.from(w.getCreatedAt()));     // Instant -> Date
        m.put("variations", w.getVariations().stream()
                .map(WordMapper::variationToMap)
                .collect(Collectors.toList()));
        return m;
    }

    private static Map<String, Object> variationToMap(DialectVariation v) {
        Map<String, Object> m = new HashMap<>();
        m.put("id", v.getId() != null ? v.getId() : UUID.randomUUID().toString());
        m.put("region", v.getRegion().name());
        m.put("writtenPronunciation", v.getWrittenPronunciation());
        m.put("audioUrl", v.getAudioUrl());
        m.put("contributedBy", v.getContributedBy());
        m.put("status", v.getStatus().name());
        m.put("createdAt", Date.from(v.getCreatedAt()));
        return m;
    }

    public static Word fromMap(String id, Map<String, Object> data) {
        Word w = new Word();
        w.setId(id);
        w.setKonkaniWord((String) data.get("konkaniWord"));
        w.setRomanized((String) data.get("romanized"));
        w.setMeaning((String) data.get("meaning"));
        w.setPartOfSpeech(PartOfSpeech.valueOf((String) data.get("partOfSpeech")));  // String -> enum
        w.setStatus(SubmissionStatus.valueOf((String) data.get("status")));
        w.setCreatedAt(toInstant(data.get("createdAt")));

        // @SuppressWarnings because the cast from Object to List<Map> is unchecked
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> rawVariations =
                (List<Map<String, Object>>) data.getOrDefault("variations", List.of());
        w.setVariations(rawVariations.stream()
                .map(WordMapper::variationFromMap)
                .collect(Collectors.toCollection(ArrayList::new)));
        return w;
    }

    private static DialectVariation variationFromMap(Map<String, Object> m) {
        DialectVariation v = new DialectVariation();
        v.setId((String) m.get("id"));
        v.setRegion(Region.valueOf((String) m.get("region")));
        v.setWrittenPronunciation((String) m.get("writtenPronunciation"));
        v.setAudioUrl((String) m.get("audioUrl"));
        v.setContributedBy((String) m.get("contributedBy"));
        v.setStatus(SubmissionStatus.valueOf((String) m.get("status")));
        v.setCreatedAt(toInstant(m.get("createdAt")));
        return v;
    }

    /** Firestore returns dates as com.google.cloud.Timestamp. */
    private static java.time.Instant toInstant(Object value) {
        return value instanceof Timestamp ts ? ts.toDate().toInstant()   // pattern matching (Java 16+)
                                             : java.time.Instant.now();
    }
}