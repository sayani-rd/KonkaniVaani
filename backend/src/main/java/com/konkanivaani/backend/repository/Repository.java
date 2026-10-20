package com.konkanivaani.backend.repository;

import java.util.List;
import java.util.Optional;

/**
 * Generic interface. T = entity type, ID = type of its identifier.
 * One contract can serve Word, User, Contribution, etc.
 */
public interface Repository<T, ID> {

    T save(T entity);

    /** Optional forces the caller to handle "not found" instead of risking null. */
    Optional<T> findById(ID id);

    List<T> findAll();

    boolean deleteById(ID id);

    // Default methods: interfaces can provide shared behaviour built on abstract ones.
    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    default long count() {
        return findAll().size();
    }
}