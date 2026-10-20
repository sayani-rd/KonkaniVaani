package com.konkanivaani.backend.model;

/** Approval workflow state for words and user contributions. */
public enum SubmissionStatus {
    PENDING, APPROVED, REJECTED;

    /** Enums can have methods. Only APPROVED items are shown to normal users. */
    public boolean isPublic() {
        return this == APPROVED;
    }
}