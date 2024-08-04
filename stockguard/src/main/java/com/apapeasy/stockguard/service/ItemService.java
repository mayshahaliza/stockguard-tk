package com.apapeasy.stockguard.service;


import com.apapeasy.stockguard.model.Item;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ItemService {

    List<Item> getAllItem();
    void addItem(Item item);
    public Item updateItem(Item item);
    public Item getItemById(Integer itemId);
    public List<Item> getItemsCloseToExpiration();// method utk filtering
    List<Item> getItemsByStatus(Integer status);
    // @Autowired
    // ItemDb itemDb;
    
    // public List<Item> getAllItem() {
    //     return itemDb.findAll();
    // }

}

