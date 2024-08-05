package com.apapeasy.stockguard.repository;

import com.apapeasy.stockguard.model.Notifikasi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotifikasiDb extends JpaRepository<Notifikasi, Integer> {

}