package com.konkanivaani.backend.dto;

/**
 * Generic record: every API response has the same shape.
 * Records are immutable data carriers; constructor, getters, equals and toString are generated.
 */
public record ApiResponse<T>(boolean success, String message, T data) {

    // Static factory methods give readable call sites
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "OK", data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }
}