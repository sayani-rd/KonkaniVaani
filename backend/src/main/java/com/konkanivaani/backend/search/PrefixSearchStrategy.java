package com.konkanivaani.backend.search;

import com.konkanivaani.backend.model.Word;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/** Matches only words that START with the query (good for autocomplete). */
@Component
public class PrefixSearchStrategy implements SearchStrategy {

    @Override
    public String name() { return "prefix"; }

    @Override
    public List<Word> search(List<Word> words, String query) {
        String q = query.trim().toLowerCase(Locale.ROOT);
        return words.stream()
                .filter(w -> startsWith(w.getKonkaniWord(), q) || startsWith(w.getRomanized(), q))
                .toList();
    }

    private boolean startsWith(String text, String q) {
        return text != null && text.toLowerCase(Locale.ROOT).startsWith(q);
    }
}