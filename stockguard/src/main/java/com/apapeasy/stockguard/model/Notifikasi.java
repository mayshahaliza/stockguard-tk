package com.apapeasy.stockguard.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifikasi")
public class Notifikasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer notifikasi_id;

    @NotNull
    @Column(name = "tanggal_notifikasi", nullable = false)
    private LocalDateTime tanggalNotifikasi;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="item_id", nullable = false)
    private Item item;
}


