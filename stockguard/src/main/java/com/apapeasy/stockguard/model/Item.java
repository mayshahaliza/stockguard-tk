package com.apapeasy.stockguard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Integer itemId;

    @NotNull
    @Column(name = "nama_item")
    private String namaItem;

    @NotNull
    @PositiveOrZero(message = "Jumlah stok tidak bisa bernilai negatif")
    @Column(name = "jumlah_stok")
    private Integer jumlahStok;

    @NotNull
    @Column(name = "status")
    private Integer status;

    @NotNull
    @Column(name = "tanggal_kadaluwarsa", nullable = false)
    private LocalDate tanggalKadaluwarsa;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<Notifikasi> notifikasi;

}


