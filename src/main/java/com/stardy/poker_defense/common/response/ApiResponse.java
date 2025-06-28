package com.stardy.poker_defense.common.response;

import com.stardy.poker_defense.common.exception.CommonErrorCode;
import com.stardy.poker_defense.common.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

@Getter
public class ApiResponse<T> {

    private final T data;
    private final String message;
    private final HttpStatus status;

    public ApiResponse(T data, String message, HttpStatus status) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

    public static <T> ApiResponseBuilder<T> builder() {
        return new ApiResponseBuilder<>();
    }

    public static class ApiResponseBuilder<T> {

        private static final ErrorCode failError = CommonErrorCode.VALIDATION_FAILED;
        private T data;
        private String message;
        private HttpHeaders headers = new HttpHeaders();
        private HttpStatus status = HttpStatus.OK;

        public ApiResponseBuilder() {
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        }

        public ApiResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        public ApiResponseBuilder<T> status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ResponseEntity<ApiResponse<T>> success() {
            return new ResponseEntity<>(
                    new ApiResponse<>(data, message, status),
                    headers,
                    status
            );
        }

        public ResponseEntity<ApiResponse<?>> error(ErrorCode errorCode) {
            return new ResponseEntity<>(
                    new ApiResponse<>(data, errorCode.getMessage(), status),
                    headers,
                    errorCode.getHttpStatus()
            );
        }

        public ResponseEntity<ApiResponse<Map<String, String>>> fail(BindingResult bindingResult) {
            return new ResponseEntity<>(
                    new ApiResponse<>(generateFailData(bindingResult), requireNonNull(message, failError.getMessage()), status),
                    headers,
                    failError.getHttpStatus());
        }

        private static Map<String, String> generateFailData(BindingResult bindingResult) {
            Map<String, String> errors = new HashMap<>();
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError error: allErrors) {
                if (error instanceof FieldError) {
                    errors.put(((FieldError) error).getField(), error.getDefaultMessage());
                } else {
                    errors.put(error.getObjectName(), error.getDefaultMessage());
                }
            }
            return errors;
        }
    }
}
