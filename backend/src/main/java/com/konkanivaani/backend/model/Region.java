package com.konkanivaani.backend.model;

/**
 * Enum: a fixed set of constants. Each constant can carry data and behaviour.
 * Edit this list to match the dialects you want to document.
 */
public enum Region {
    GOA("Goan"),
    MANGALORE("Mangalorean"),
    KARWAR("Karwari"),
    SHIVAMOGGA("Shivamogga"),
    KERALA("Kerala (Cochin)");

    private final String displayName;   // encapsulated field

    Region(String displayName) {        // enum constructors are always private
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}