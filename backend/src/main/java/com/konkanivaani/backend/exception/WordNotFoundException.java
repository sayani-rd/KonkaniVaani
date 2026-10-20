package com.konkanivaani.backend.exception;

/**
 * Custom unchecked exception. Extending RuntimeException means callers
 * are not forced to catch it; a global handler will convert it to HTTP 404 later.
 */
public class WordNotFoundException extends RuntimeException {

    private final String wordId;

    public WordNotFoundException(String wordId) {
        super("Word not found with id: " + wordId);   // message passed to parent class
        this.wordId = wordId;
    }

    public String getWordId() {
        return wordId;
    }
}