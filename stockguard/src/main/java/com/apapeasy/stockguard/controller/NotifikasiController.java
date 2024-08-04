package com.apapeasy.stockguard.controller;

import com.apapeasy.stockguard.model.Item;
import com.apapeasy.stockguard.model.Notifikasi;
import com.apapeasy.stockguard.model.User;
import com.apapeasy.stockguard.service.AuthenticationService;
import com.apapeasy.stockguard.service.ItemService;
import com.apapeasy.stockguard.service.NotifikasiService;
import com.apapeasy.stockguard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NotifikasiController {
    @Autowired
    private NotifikasiService notifikasiService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/notifikasi/add/{itemId}")
    public String NotificationForm(@PathVariable("itemId") Integer itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        Notifikasi notifikasi = new Notifikasi();
        notifikasi.setItem(item);
        notifikasi.setTanggalNotifikasi(LocalDateTime.now());

        model.addAttribute("item", item);
        model.addAttribute("notifikasi", notifikasi);
        model.addAttribute("messages", List.of("Stok Ulang", "Pengembalian", "Penghapusan"));
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "notifikasi/add-notifikasi";
    }

    @PostMapping("/notifikasi/add/{itemId}")
    public String saveNotification(@ModelAttribute("notifikasi") Notifikasi notifikasi, @PathVariable("itemId") Integer itemId, Model model) {
        Item item = itemService.getItemById(itemId);
        notifikasi.setItem(item);
        notifikasi.setTanggalNotifikasi(LocalDateTime.now());
        notifikasi.setUser(authenticationService.getLoggedInUser());
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        notifikasiService.addNotifikasi(notifikasi);
        return "notifikasi/success-add-notifikasi";
    }

    @GetMapping("/notifikasi")
    public String viewAllNotifications(Model model) {
        List<Notifikasi> listNotifikasi = notifikasiService.getAllNotifikasi();
        model.addAttribute("listNotifikasi", listNotifikasi);
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        return "notifikasi/view-all-notifikasi";
    }
}
