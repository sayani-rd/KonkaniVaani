package com.konkanivaani.backend.model;

import java.time.Instant;
import java.util.Objects;

/**
 * Abstract class: cannot be instantiated directly.
 * Holds the fields shared by every stored object (inheritance).
 */
public abstract class BaseEntity {

    private String id;
    private Instant createdAt;

    protected BaseEntity() {                 // Firestore needs a no-arg constructor
        this.createdAt = Instant.now();
    }

    // Getters and setters = encapsulation (fields are private)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    /** Each subclass describes itself differently (polymorphism). */
    public abstract String describe();

    // Two entities are equal if they have the same id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}