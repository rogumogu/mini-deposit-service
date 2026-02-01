package com.rog.deposit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    @JsonProperty("ErrorCode")
    private String errorCode;
    
    @JsonProperty("ErrorSource")
    private String errorSource;
    
    @JsonProperty("ErrorReason")
    private String errorReason;
}
