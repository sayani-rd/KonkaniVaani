package com.konkanivaani.backend.model;

/** One regional pronunciation of a word. Extends BaseEntity (inheritance). */
public class DialectVariation extends BaseEntity {

    private Region region;
    private String writtenPronunciation;
    private String audioUrl;                 // may be null if no recording yet
    private String contributedBy;            // user id
    private SubmissionStatus status = SubmissionStatus.PENDING;

    public DialectVariation() { }            // required for Firestore mapping

    // Overloaded constructor (compile-time polymorphism)
    public DialectVariation(Region region, String writtenPronunciation, String contributedBy) {
        this.region = region;
        this.writtenPronunciation = writtenPronunciation;
        this.contributedBy = contributedBy;
    }

    public boolean hasAudio() {
        return audioUrl != null && !audioUrl.isBlank();
    }

    @Override
    public String describe() {               // overriding (runtime polymorphism)
        return region.getDisplayName() + ": " + writtenPronunciation;
    }

    public Region getRegion() { return region; }
    public void setRegion(Region region) { this.region = region; }

    public String getWrittenPronunciation() { return writtenPronunciation; }
    public void setWrittenPronunciation(String p) { this.writtenPronunciation = p; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }

    public String getContributedBy() { return contributedBy; }
    public void setContributedBy(String c) { this.contributedBy = c; }

    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus status) { this.status = status; }
}