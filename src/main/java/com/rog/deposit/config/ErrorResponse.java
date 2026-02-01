package com.rog.deposit.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorResponse {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int statusCode;
    private String errorCode;
    private String errorSource;
    private String errorReason;

    public ErrorResponse copy() {
        return new ErrorResponse(statusCode, errorCode, errorSource, errorReason);
    }

    public void setSourceParameters(Object... args) {
        errorSource = errorSource.formatted(args);
    }

    public void setReasonParameters(Object... args) {
        errorReason = errorReason.formatted(args);
    }

    public ResponseEntity<ErrorResponse> toResponseEntity() {
        return ResponseEntity.status(statusCode).body(this);
    }

}
