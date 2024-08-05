package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Item;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface ItemService {

    List<Item> getAllItem();
    void addItem(Item item);
    Item updateItem(Item item);
    Item getItemById(Integer itemId);
    List<Item> getItemsCloseToExpiration();
    List<Item> getItemsByStatus(Integer status);


}

