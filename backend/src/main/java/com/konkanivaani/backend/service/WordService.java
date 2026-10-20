package com.konkanivaani.backend.service;

import com.konkanivaani.backend.dto.WordRequest;
import com.konkanivaani.backend.exception.BadRequestException;
import com.konkanivaani.backend.model.SubmissionStatus;
import com.konkanivaani.backend.model.Word;
import com.konkanivaani.backend.repository.WordRepository;
import com.konkanivaani.backend.search.SearchStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/** Business logic layer. Controllers call this; this calls the repository. */
@Service
public class WordService {

    private final WordRepository repository;
    private final Map<String, SearchStrategy> strategies;   // name -> strategy

    // Spring injects ALL SearchStrategy beans as a List; we index them by name
    public WordService(WordRepository repository, List<SearchStrategy> strategyList) {
        this.repository = repository;
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(SearchStrategy::name, Function.identity()));
    }

    /** Public listing: only approved words, sorted by romanized text. */
    public List<Word> listApproved() {
        return repository.findByStatus(SubmissionStatus.APPROVED).stream()
                .sorted((a, b) -> a.getRomanized().compareToIgnoreCase(b.getRomanized()))
                .toList();
    }

    public Word getApproved(String id) {
        Word w = repository.getOrThrow(id);
        if (!w.getStatus().isPublic()) {
            throw new com.konkanivaani.backend.exception.WordNotFoundException(id);
        }
        return w;
    }

    public List<Word> search(String query, String strategyName) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        SearchStrategy strategy = strategies.get(strategyName == null ? "contains" : strategyName);
        if (strategy == null) {
            throw new BadRequestException("Unknown search strategy. Use one of: " + strategies.keySet());
        }
        return strategy.search(listApproved(), query);
    }

    /** Temporary until auth (Phase 5): new words are saved as APPROVED so you can see them. */
    public Word create(WordRequest req) {
        Word w = new Word(req.konkaniWord(), req.romanized(), req.meaning(), req.partOfSpeech());
        w.setStatus(SubmissionStatus.APPROVED);
        return repository.save(w);
    }
}