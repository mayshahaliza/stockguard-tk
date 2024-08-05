package com.apapeasy.stockguard.controller;

import com.apapeasy.stockguard.dto.CreateItemRequestDTO;
import com.apapeasy.stockguard.model.Category;
import com.apapeasy.stockguard.model.Item;
import com.apapeasy.stockguard.model.Notifikasi;
import com.apapeasy.stockguard.model.User;
import com.apapeasy.stockguard.dto.ItemMapper;
import com.apapeasy.stockguard.dto.UpdateItemRequestDTO;
import com.apapeasy.stockguard.repository.ItemDb;
import com.apapeasy.stockguard.repository.NotifikasiDb;
import com.apapeasy.stockguard.service.AuthenticationService;
import com.apapeasy.stockguard.service.CategoryService;
import com.apapeasy.stockguard.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    ItemDb itemRepository;

    @Autowired
    NotifikasiDb notifikasiDb;

    @Autowired
    private ItemMapper itemMapper;

    @GetMapping("/item")
    public String listItem(@RequestParam(name = "status", required = false) Integer status, Model model) {
        List<Item> listItem;
        if (status != null) {
            listItem = itemService.getItemsByStatus(status);
        } else {
            listItem = itemRepository.findAll();
        }
        model.addAttribute("listItem", listItem);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/view-all-item";
    }

    @GetMapping("/item/add")
    public String formAddItem(Model model) {
        CreateItemRequestDTO itemDTO = new CreateItemRequestDTO();
        List<Category> listCategory = categoryService.getAllCategory();

        model.addAttribute("listCategory", listCategory);
        model.addAttribute("itemDTO", itemDTO);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/form-add-item";
    }

    @PostMapping("/item/add")
    public String addItemSubmit(@Valid @ModelAttribute("itemDTO") CreateItemRequestDTO itemDTO, Model model) {
        Item item = itemMapper.createItemRequestDTOToItem(itemDTO);
        itemService.addItem(item);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/success-add-item";
    }

    @GetMapping("/item/update/{itemId}")
    public String formUpdateItem(@PathVariable("itemId") Integer itemId,
                                 Model model) {
        var item = itemService.getItemById(itemId);
        var itemDTO = itemMapper.itemToUpdateItemRequestDTO(item);
        var listCategory = categoryService.getAllCategory();

        model.addAttribute("itemDTO", itemDTO);
        model.addAttribute("listCategory", listCategory);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/form-update-item";
    }

    @PostMapping("/item/update/{itemId}")
    public String submitFormUpdateItem(@PathVariable("itemId") Integer itemId,
                                       @Valid @ModelAttribute("itemDTO") UpdateItemRequestDTO itemDTO,
                                       Model model) {

        var itemFromDto = itemMapper.updateItemRequestDTOToItem(itemDTO);
        var item = itemService.updateItem(itemFromDto);
        model.addAttribute("itemId", item.getItemId());

        if(item.getStatus().equals(0)){
            for(Notifikasi notif : notifikasiDb.findAll()){
                if(notif.getItem()==item){
                    notifikasiDb.delete(notif);
                }
            }
        }

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/success-update-item";
    }

    @GetMapping("/item/view-close-to-expiration")
    public String viewCloseToExpiration(Model model) {
        List<Item> items = itemService.getItemsCloseToExpiration();
        model.addAttribute("items", items);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/view-close-to-expiration";
    }

    @GetMapping("/item/detail/{itemId}")
    public String detailItem(@PathVariable("itemId") Integer itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        model.addAttribute("item", item);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "item/detail-item";
    }
}