package com.konkanivaani.backend.repository;

import com.konkanivaani.backend.exception.WordNotFoundException;
import com.konkanivaani.backend.model.PartOfSpeech;
import com.konkanivaani.backend.model.SubmissionStatus;
import com.konkanivaani.backend.model.Word;

import java.util.List;
import java.util.Map;

/** Word-specific queries on top of the generic CRUD contract. */
public interface WordRepository extends Repository<Word, String> {

    List<Word> findByStatus(SubmissionStatus status);

    /** Case-insensitive search on Konkani text, romanized text and meaning. */
    List<Word> search(String query);

    /** Groups words by part of speech (used for browsing/statistics). */
    Map<PartOfSpeech, List<Word>> groupByPartOfSpeech();

    /** Default method combining Optional with a custom exception. */
    default Word getOrThrow(String id) {
        return findById(id).orElseThrow(() -> new WordNotFoundException(id));
    }
}