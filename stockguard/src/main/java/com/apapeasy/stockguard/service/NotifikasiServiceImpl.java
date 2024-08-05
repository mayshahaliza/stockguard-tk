package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Notifikasi;
import com.apapeasy.stockguard.repository.NotifikasiDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotifikasiServiceImpl implements NotifikasiService {
    @Autowired
    private NotifikasiDb notifikasiDb;

    public void addNotifikasi(Notifikasi notifikasi){
        notifikasiDb.save(notifikasi);
    }

    public List<Notifikasi> getAllNotifikasi(){
        return notifikasiDb.findAll();
    }
}
