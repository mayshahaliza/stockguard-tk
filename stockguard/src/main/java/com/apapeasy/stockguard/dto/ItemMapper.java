package com.apapeasy.stockguard.dto;

import com.apapeasy.stockguard.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "tanggalKadaluwarsa", target = "tanggalKadaluwarsa", dateFormat = "yyyy-MM-dd")
    Item createItemRequestDTOToItem(CreateItemRequestDTO dto);

    Item updateItemRequestDTOToItem(UpdateItemRequestDTO updateItemRequestDTO);
    UpdateItemRequestDTO itemToUpdateItemRequestDTO(Item item);
}

