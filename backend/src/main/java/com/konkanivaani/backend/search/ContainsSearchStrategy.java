package com.konkanivaani.backend.search;

import com.konkanivaani.backend.model.Word;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/** Matches if the query appears anywhere in the word, romanized text or meaning. */
@Component
public class ContainsSearchStrategy implements SearchStrategy {

    @Override
    public String name() { return "contains"; }

    @Override
    public List<Word> search(List<Word> words, String query) {
        String q = query.trim().toLowerCase(Locale.ROOT);
        return words.stream()
                .filter(w -> has(w.getKonkaniWord(), q) || has(w.getRomanized(), q) || has(w.getMeaning(), q))
                .toList();
    }

    private boolean has(String text, String q) {
        return text != null && text.toLowerCase(Locale.ROOT).contains(q);
    }
}