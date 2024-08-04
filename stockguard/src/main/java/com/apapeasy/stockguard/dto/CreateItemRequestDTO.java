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
    //private Integer categoryId;


//    @NotNull
//    private String namaItem;
//
//    @NotNull
//    @PositiveOrZero(message = "Jumlah stok tidak bisa bernilai negatif")
//    private Integer jumlahStok;
//
//    @NotNull
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date tanggalKadaluwarsa;
//
//    @NotNull
//    private Integer categoryId;
//
//    // Getters and setters
//
//    public String getNamaItem() {
//        return namaItem;
//    }
//
//    public void setNamaItem(String namaItem) {
//        this.namaItem = namaItem;
//    }
//
//    public Integer getJumlahStok() {
//        return jumlahStok;
//    }
//
//    public void setJumlahStok(Integer jumlahStok) {
//        this.jumlahStok = jumlahStok;
//    }
//
//    public Date getTanggalKadaluwarsa() {
//        return tanggalKadaluwarsa;
//    }
//
//    public void setTanggalKadaluwarsa(Date tanggalKadaluwarsa) {
//        this.tanggalKadaluwarsa = tanggalKadaluwarsa;
//    }
//
//    public Integer getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }

}





