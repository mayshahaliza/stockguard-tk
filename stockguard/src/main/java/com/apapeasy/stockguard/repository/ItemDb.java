package com.apapeasy.stockguard.repository;

import com.apapeasy.stockguard.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ItemDb extends JpaRepository<Item, Integer> {

    //     List<Item> findAll();
    Item findByitemId(Integer itemId);

    @Query("SELECT i FROM Item i WHERE i.tanggalKadaluwarsa <= :date")
    List<Item> findItemsCloseToExpiration(@Param("date") LocalDate date);



//     List<Item> findByKategoriId(Long kategoriId);
//     List<Item> findByNamaItemContaining(String namaItem);
//     List<Item> findByKategoriIdAndNamaItemContaining(Long kategoriId, String itemName);

//     @Query("SELECT i FROM Item i WHERE i.tanggalKadaluwarsa <= :endDate AND i.tanggalKadaluwarsa >= :startDate")
//     List<Item> findItemsCloseToExpiration(Date startDate, Date endDate);
}


