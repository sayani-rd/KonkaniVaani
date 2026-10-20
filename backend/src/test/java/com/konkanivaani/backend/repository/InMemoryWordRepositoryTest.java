package com.konkanivaani.backend.repository;

import com.konkanivaani.backend.exception.WordNotFoundException;
import com.konkanivaani.backend.model.PartOfSpeech;
import com.konkanivaani.backend.model.SubmissionStatus;
import com.konkanivaani.backend.model.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryWordRepositoryTest {

    private WordRepository repo;

    @BeforeEach
    void setUp() {
        repo = new InMemoryWordRepository();      // programming to the interface
    }

    @Test
    void saveGeneratesIdAndCanBeFound() {
        Word saved = repo.save(new Word("उदक", "udak", "water", PartOfSpeech.NOUN));
        assertNotNull(saved.getId());
        assertTrue(repo.findById(saved.getId()).isPresent());
        assertEquals(1, repo.count());
    }

    @Test
    void searchIsCaseInsensitiveAcrossFields() {
        repo.save(new Word("उदक", "udak", "water", PartOfSpeech.NOUN));
        repo.save(new Word("भात", "bhat", "cooked rice", PartOfSpeech.NOUN));

        assertEquals(1, repo.search("WATER").size());
        assertEquals(1, repo.search("bha").size());
        assertTrue(repo.search("   ").isEmpty());
    }

    @Test
    void findByStatusFiltersCorrectly() {
        Word a = repo.save(new Word("उदक", "udak", "water", PartOfSpeech.NOUN));
        a.setStatus(SubmissionStatus.APPROVED);
        repo.save(new Word("भात", "bhat", "rice", PartOfSpeech.NOUN));

        assertEquals(1, repo.findByStatus(SubmissionStatus.APPROVED).size());
        assertEquals(1, repo.findByStatus(SubmissionStatus.PENDING).size());
    }

    @Test
    void groupByPartOfSpeechGroupsWords() {
        repo.save(new Word("उदक", "udak", "water", PartOfSpeech.NOUN));
        repo.save(new Word("खा", "kha", "eat", PartOfSpeech.VERB));

        Map<PartOfSpeech, List<Word>> grouped = repo.groupByPartOfSpeech();
        assertEquals(2, grouped.size());
        assertEquals(1, grouped.get(PartOfSpeech.VERB).size());
    }

    @Test
    void getOrThrowThrowsCustomException() {
        assertThrows(WordNotFoundException.class, () -> repo.getOrThrow("missing-id"));
    }

    @Test
    void deleteRemovesWord() {
        Word saved = repo.save(new Word("उदक", "udak", "water", PartOfSpeech.NOUN));
        assertTrue(repo.deleteById(saved.getId()));
        assertFalse(repo.existsById(saved.getId()));
    }
}