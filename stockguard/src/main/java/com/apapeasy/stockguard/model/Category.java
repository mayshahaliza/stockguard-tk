package com.apapeasy.stockguard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer category_id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String categoryName;

    //relation ke barang
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> items;
}

