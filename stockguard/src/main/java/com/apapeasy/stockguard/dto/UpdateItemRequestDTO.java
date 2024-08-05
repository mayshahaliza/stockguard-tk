package com.apapeasy.stockguard.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateItemRequestDTO extends CreateItemRequestDTO {
    @NotNull
    private Integer itemId;
}

