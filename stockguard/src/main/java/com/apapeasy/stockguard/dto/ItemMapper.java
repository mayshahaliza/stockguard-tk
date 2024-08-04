package com.apapeasy.stockguard.dto;

import com.apapeasy.stockguard.dto.CreateItemRequestDTO;
//import com.apapeasy.stockguard.dto.UpdateItemRequestDTO;
import com.apapeasy.stockguard.model.Item;
import com.apapeasy.stockguard.model.Category;
import com.apapeasy.stockguard.model.Item;
import com.apapeasy.stockguard.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.format.annotation.DateTimeFormat;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "tanggalKadaluwarsa", target = "tanggalKadaluwarsa", dateFormat = "yyyy-MM-dd")
    Item createItemRequestDTOToItem(CreateItemRequestDTO dto);

    //Item createItemRequestDTOToItem(CreateItemRequestDTO createItemRequestDTO);

    Item updateItemRequestDTOToItem(UpdateItemRequestDTO updateItemRequestDTO);
    UpdateItemRequestDTO itemToUpdateItemRequestDTO(Item item);
}


//@Component
//public class ItemMapper {
//
//    @Autowired
//    private CategoryService categoryService;
//
//    public Item createItemRequestDTOToItem(CreateItemRequestDTO itemDTO) {
//        Item item = new Item();
//        item.setNamaItem(itemDTO.getNamaItem());
//        item.setJumlahStok(itemDTO.getJumlahStok());
//        item.setTanggalKadaluwarsa(itemDTO.getTanggalKadaluwarsa());
//
//        Category category = categoryService.getCategoryById(itemDTO.getCategoryId());
//        item.setCategory(category);
//
//        return item;
//    }
//}

