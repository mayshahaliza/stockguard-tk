package com.apapeasy.stockguard.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequestDTO {

    private Integer category_id;

    @NotNull
    private String categoryName;

    //relation ke barang
}