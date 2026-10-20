package com.konkanivaani.backend.repository;

import com.google.cloud.firestore.*;
import com.konkanivaani.backend.exception.DataAccessException;
import com.konkanivaani.backend.model.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/** Firestore implementation of the same WordRepository contract. */
@Repository
@Profile("!memory")
public class FirestoreWordRepository implements WordRepository {

    private static final String COLLECTION = "words";
    private final CollectionReference words;

    // Constructor injection: Spring passes in the Firestore bean from FirebaseConfig
    public FirestoreWordRepository(Firestore firestore) {
        this.words = firestore.collection(COLLECTION);
    }

    @Override
    public Word save(Word word) {
        try {
            DocumentReference ref = (word.getId() == null)
                    ? words.document()                  // Firestore generates an id
                    : words.document(word.getId());
            word.setId(ref.getId());
            ref.set(WordMapper.toMap(word)).get();      // .get() waits for the write
            return word;
        } catch (ExecutionException | InterruptedException e) {   // multi-catch
            throw wrap("save word", e);
        }
    }

    @Override
    public Optional<Word> findById(String id) {
        try {
            DocumentSnapshot snap = words.document(id).get().get();
            return snap.exists()
                    ? Optional.of(WordMapper.fromMap(snap.getId(), snap.getData()))
                    : Optional.empty();
        } catch (ExecutionException | InterruptedException e) {
            throw wrap("read word " + id, e);
        }
    }

    @Override
    public List<Word> findAll() {
        try {
            return words.get().get().getDocuments().stream()
                    .map(d -> WordMapper.fromMap(d.getId(), d.getData()))
                    .collect(Collectors.toList());
        } catch (ExecutionException | InterruptedException e) {
            throw wrap("list words", e);
        }
    }

    @Override
    public boolean deleteById(String id) {
        if (!existsById(id)) {
            return false;
        }
        try {
            words.document(id).delete().get();
            return true;
        } catch (ExecutionException | InterruptedException e) {
            throw wrap("delete word " + id, e);
        }
    }

    @Override
    public List<Word> findByStatus(SubmissionStatus status) {
        try {
            return words.whereEqualTo("status", status.name()).get().get().getDocuments().stream()
                    .map(d -> WordMapper.fromMap(d.getId(), d.getData()))
                    .collect(Collectors.toList());
        } catch (ExecutionException | InterruptedException e) {
            throw wrap("query words by status", e);
        }
    }

    /** Firestore has no built-in text search, so we filter in Java. Fine for a dictionary-sized dataset. */
    @Override
    public List<Word> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        String q = query.trim().toLowerCase(Locale.ROOT);
        return findAll().stream()
                .filter(w -> contains(w.getKonkaniWord(), q)
                        || contains(w.getRomanized(), q)
                        || contains(w.getMeaning(), q))
                .sorted((a, b) -> a.getRomanized().compareToIgnoreCase(b.getRomanized()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<PartOfSpeech, List<Word>> groupByPartOfSpeech() {
        return findAll().stream().collect(Collectors.groupingBy(Word::getPartOfSpeech));
    }

    private boolean contains(String text, String lowerQuery) {
        return text != null && text.toLowerCase(Locale.ROOT).contains(lowerQuery);
    }

    /** Restores the interrupt flag if needed, then wraps in our own exception. */
    private DataAccessException wrap(String action, Exception e) {
        if (e instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        return new DataAccessException("Failed to " + action, e);
    }
}