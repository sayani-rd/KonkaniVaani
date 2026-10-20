package com.konkanivaani.backend.controller;

import com.konkanivaani.backend.dto.ApiResponse;
import com.konkanivaani.backend.dto.WordRequest;
import com.konkanivaani.backend.dto.WordResponse;
import com.konkanivaani.backend.service.WordService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST controller: translates HTTP requests into service calls. */
@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService service;

    public WordController(WordService service) {
        this.service = service;
    }

    @GetMapping
    public ApiResponse<List<WordResponse>> list() {
        return ApiResponse.ok(service.listApproved().stream().map(WordResponse::from).toList());
    }

    @GetMapping("/{id}")
    public ApiResponse<WordResponse> get(@PathVariable String id) {
        return ApiResponse.ok(WordResponse.from(service.getApproved(id)));
    }

    @GetMapping("/search")
    public ApiResponse<List<WordResponse>> search(@RequestParam String q,
                                                  @RequestParam(required = false) String mode) {
        return ApiResponse.ok(service.search(q, mode).stream().map(WordResponse::from).toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<WordResponse> create(@Valid @RequestBody WordRequest req) {
        return ApiResponse.ok(WordResponse.from(service.create(req)));
    }
}