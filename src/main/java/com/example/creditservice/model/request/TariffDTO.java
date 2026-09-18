package com.example.creditservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TariffDTO {
    @NotBlank(message = "type is required")
    private String type;

    @JsonProperty("interest_rate")
    @NotBlank(message = "interest rate is required")
    private String interestRate;
}
