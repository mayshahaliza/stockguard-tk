package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Item;
import com.apapeasy.stockguard.repository.ItemDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDb itemDb;

    public List<Item> getAllItem(){
        return itemDb.findAll();
    }

    public void addItem(Item item){
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        if(item.getTanggalKadaluwarsa().isAfter(today.minusDays(1)) && item.getTanggalKadaluwarsa().isBefore(nextWeek)){
            item.setStatus(1);
        }
        else{
            item.setStatus(0);
        }

        itemDb.save(item);
    }

    public Item updateItem(Item item) {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        if(item.getTanggalKadaluwarsa().isAfter(today.minusDays(1)) && item.getTanggalKadaluwarsa().isBefore(nextWeek)){
            item.setStatus(1);
        }
        else{
            item.setStatus(0);
        }
        return itemDb.save(item);
    }

    public Item getItemById(Integer itemId) {
        return itemDb.findByitemId(itemId);
    }

    // items close to expired asumsi jarak 7 hari dari tanggal real-time ke tanggalKadaluwarsa
    public List<Item> getItemsCloseToExpiration() {
        LocalDate date = LocalDate.now().plusDays(7);
        return itemDb.findItemsCloseToExpiration(date);
    }

    public List<Item> getItemsByStatus(Integer status) {
        List<Item> listItem = itemDb.findAll();
        List<Item> itemsByStatus = new ArrayList<Item>();
        for (Item item : listItem) {
            if (item.getStatus() == status) {
                itemsByStatus.add(item);
            }
        }
        return itemsByStatus;
    }



}

