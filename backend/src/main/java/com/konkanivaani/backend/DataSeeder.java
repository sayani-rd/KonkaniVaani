package com.konkanivaani.backend;

import com.konkanivaani.backend.model.*;
import com.konkanivaani.backend.repository.WordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/** Runs once at startup, only when the "seed" profile is active. */
@Component
@Profile("seed")
public class DataSeeder implements CommandLineRunner {

    private final WordRepository repo;

    public DataSeeder(WordRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        Word w = new Word("उदक", "udak", "water", PartOfSpeech.NOUN);
        w.setStatus(SubmissionStatus.APPROVED);
        DialectVariation v = new DialectVariation(Region.GOA, "udak", "seed-user");
        v.setStatus(SubmissionStatus.APPROVED);
        w.addVariation(v);

        Word saved = repo.save(w);
        System.out.println("SAVED: " + saved.describe() + " id=" + saved.getId());
        System.out.println("SEARCH 'wat': " + repo.search("wat").size() + " result(s)");
        System.out.println("MISSING REGIONS: " + saved.getMissingRegions());
    }
}