package com.konkanivaani.backend.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** A dictionary entry with its dialect variations. */
public class Word extends BaseEntity {

    private String konkaniWord;
    private String romanized;
    private String meaning;
    private PartOfSpeech partOfSpeech = PartOfSpeech.OTHER;
    private SubmissionStatus status = SubmissionStatus.PENDING;
    private List<DialectVariation> variations = new ArrayList<>();  // Collections

    public Word() { }

    public Word(String konkaniWord, String romanized, String meaning, PartOfSpeech pos) {
        this.konkaniWord = konkaniWord;
        this.romanized = romanized;
        this.meaning = meaning;
        this.partOfSpeech = pos;
    }

    /** Adds a variation; defensive check avoids duplicates per region. */
    public void addVariation(DialectVariation v) {
        boolean exists = variations.stream()
                .anyMatch(x -> x.getRegion() == v.getRegion()
                        && x.getWrittenPronunciation().equalsIgnoreCase(v.getWrittenPronunciation()));
        if (!exists) {
            variations.add(v);
        }
    }

    /**
     * Missing-dialect tracking: all regions MINUS the regions already covered.
     * EnumSet is a fast, compact Set built specially for enums.
     */
    public Set<Region> getMissingRegions() {
        Set<Region> missing = EnumSet.allOf(Region.class);
        Set<Region> covered = variations.stream()          // Streams + lambdas
                .filter(v -> v.getStatus().isPublic())
                .map(DialectVariation::getRegion)          // method reference
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Region.class)));
        missing.removeAll(covered);
        return missing;
    }

    @Override
    public String describe() {
        return konkaniWord + " (" + romanized + ") - " + meaning;
    }

    public String getKonkaniWord() { return konkaniWord; }
    public void setKonkaniWord(String k) { this.konkaniWord = k; }

    public String getRomanized() { return romanized; }
    public void setRomanized(String r) { this.romanized = r; }

    public String getMeaning() { return meaning; }
    public void setMeaning(String m) { this.meaning = m; }

    public PartOfSpeech getPartOfSpeech() { return partOfSpeech; }
    public void setPartOfSpeech(PartOfSpeech p) { this.partOfSpeech = p; }

    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus s) { this.status = s; }

    public List<DialectVariation> getVariations() { return variations; }
    public void setVariations(List<DialectVariation> v) { this.variations = v; }
}