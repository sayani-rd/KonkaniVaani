package com.konkanivaani.backend.repository;

import com.konkanivaani.backend.model.PartOfSpeech;
import com.konkanivaani.backend.model.SubmissionStatus;
import com.konkanivaani.backend.model.Word;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Stores words in memory. ConcurrentHashMap is thread-safe, which matters
 * because a web server handles many requests on different threads at once.
 * The @Repository annotation registers this class as a Spring bean.
 */
@Repository
@Profile("memory") 
public class InMemoryWordRepository implements WordRepository {

    private final Map<String, Word> store = new ConcurrentHashMap<>();

    @Override
    public Word save(Word word) {
        if (word.getId() == null) {
            word.setId(UUID.randomUUID().toString());   // generate id for new words
        }
        store.put(word.getId(), word);
        return word;
    }

    @Override
    public Optional<Word> findById(String id) {
        return Optional.ofNullable(store.get(id));       // wraps a possible null safely
    }

    @Override
    public List<Word> findAll() {
        return new ArrayList<>(store.values());          // defensive copy
    }

    @Override
    public boolean deleteById(String id) {
        return store.remove(id) != null;
    }

    @Override
    public List<Word> findByStatus(SubmissionStatus status) {
        return store.values().stream()                   // Stream pipeline
                .filter(w -> w.getStatus() == status)    // lambda
                .collect(Collectors.toList());
    }

    @Override
    public List<Word> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();                            // immutable empty list
        }
        String q = query.trim().toLowerCase(Locale.ROOT);
        return store.values().stream()
                .filter(w -> contains(w.getKonkaniWord(), q)
                        || contains(w.getRomanized(), q)
                        || contains(w.getMeaning(), q))
                .sorted((a, b) -> a.getRomanized().compareToIgnoreCase(b.getRomanized()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<PartOfSpeech, List<Word>> groupByPartOfSpeech() {
        return store.values().stream()
                .collect(Collectors.groupingBy(Word::getPartOfSpeech));   // method reference
    }

    /** Private helper: null-safe, case-insensitive "contains". */
    private boolean contains(String text, String lowerQuery) {
        return text != null && text.toLowerCase(Locale.ROOT).contains(lowerQuery);
    }
}