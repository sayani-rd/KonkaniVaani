package com.konkanivaani.backend.search;

import com.konkanivaani.backend.model.Word;

import java.util.List;

/** Strategy pattern: each search algorithm is a swappable implementation. */
public interface SearchStrategy {
    String name();
    List<Word> search(List<Word> words, String query);
}