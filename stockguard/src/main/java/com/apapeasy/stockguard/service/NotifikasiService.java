package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Notifikasi;

import java.util.List;

public interface NotifikasiService {
    void addNotifikasi(Notifikasi notifikasi);
    List<Notifikasi> getAllNotifikasi();
}
