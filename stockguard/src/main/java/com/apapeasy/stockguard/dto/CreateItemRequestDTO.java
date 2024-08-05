package com.apapeasy.stockguard.dto;

import com.apapeasy.stockguard.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequestDTO {
    private Integer itemId;
    private String namaItem;
    private Integer jumlahStok;
    private Integer status;
    private LocalDate tanggalKadaluwarsa;
    private Category category;

}





