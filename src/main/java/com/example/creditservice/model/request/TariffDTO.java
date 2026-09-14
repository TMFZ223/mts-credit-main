package com.example.creditservice.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TariffDTO {
    @NotBlank(message = "type is required")
    private String type;

    @NotBlank(message = "interest rate is required")
    private String interest_rate;
}
