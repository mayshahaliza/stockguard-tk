package com.apapeasy.stockguard.controller;
import com.apapeasy.stockguard.model.Item;
import com.apapeasy.stockguard.model.Notifikasi;
import com.apapeasy.stockguard.model.User;
import com.apapeasy.stockguard.repository.UserDb;
import com.apapeasy.stockguard.service.AuthenticationService;
import com.apapeasy.stockguard.service.ItemService;
import com.apapeasy.stockguard.service.NotifikasiService;
import com.apapeasy.stockguard.service.UserService;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    ItemService itemService;

    @Autowired
    UserDb userRepository;

    @Autowired
    NotifikasiService notifikasiService;

    @GetMapping("/")
    public String landingPage(Model model){
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        List<Notifikasi> listNotifikasi = notifikasiService.getAllNotifikasi();
        if (listNotifikasi == null){
            listNotifikasi = new ArrayList<>();
        }
        model.addAttribute("listNotifikasi", listNotifikasi);
        model.addAttribute("jumlahNotifikasi", listNotifikasi.size());
        List<Item> items = itemService.getItemsCloseToExpiration();
        // Nampilkan 10 data teratas
        items = items.stream()
                .limit(10)
                .collect(Collectors.toList());
        model.addAttribute("items", items);
        //model.addAttribute("hasNotifications", !listNotifikasi.isEmpty());
        if (loggedInUser == null) {
            return "redirect:/login";
        } else {
            return "home";
        }
    }

    @GetMapping("/login")
    public String login(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        String username = user.getUsername();
        User userData = userRepository.findByUsername(username);

        if (userData != null && user.getPassword().equals(userData.getPassword())) {
            authenticationService.addLoggedInUser(userData);
            User loggedInUser = authenticationService.getLoggedInUser();
            model.addAttribute("user", loggedInUser);
            List<Notifikasi> listNotifikasi = notifikasiService.getAllNotifikasi();
            if (listNotifikasi == null){
                listNotifikasi = new ArrayList<>();
            }
            model.addAttribute("listNotifikasi", listNotifikasi);
            model.addAttribute("jumlahNotifikasi", listNotifikasi.size());
            List<Item> items = itemService.getItemsCloseToExpiration();
            model.addAttribute("items", items);
            return "home";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @PostMapping("/logout")
    public String logout() {
        authenticationService.removeLoggedInUser();
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String listUser(Model model) {
        User loggedInUser = authenticationService.getLoggedInUser();

        List<User> listUser = userRepository.findAll();

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", loggedInUser);
        return "user/viewall-user";
    }

    @GetMapping("/no-access")
    public String showNoAccessPage() {
        return "no-access";
    }


    @GetMapping("/register")
    public String register(Model model){
        User userRegister = new User();
        model.addAttribute("userRegister", userRegister);
 
 
        User user = authenticationService.getLoggedInUser();
        model.addAttribute("user", user);
        return "register";
    }
 
 
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("userRegister") @Valid User userRegister, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRegister", userRegister);
            model.addAttribute("errors", bindingResult.getAllErrors());


            User user = authenticationService.getLoggedInUser();
            model.addAttribute("user", user);
            return "register";
        }


        if (userService.existsByUsername(userRegister.getUsername()) || userService.existsByEmail(userRegister.getEmail())) {
            // Handle duplicate username or email
            model.addAttribute("duplicateError", "Username atau email sudah terdaftar.");
            User user = authenticationService.getLoggedInUser();
            model.addAttribute("userRegister", userRegister);
            model.addAttribute("user", user);
            return "register";
        }

        User user = authenticationService.getLoggedInUser();
        model.addAttribute("user", user);
        userService.registerUser(userRegister);
        List<Notifikasi> listNotifikasi = notifikasiService.getAllNotifikasi();
        if (listNotifikasi == null){
            listNotifikasi = new ArrayList<>();
        }
        model.addAttribute("listNotifikasi", listNotifikasi);
        model.addAttribute("jumlahNotifikasi", listNotifikasi.size());
        List<Item> items = itemService.getItemsCloseToExpiration();
        model.addAttribute("items", items);
        return "home";
    }


    // VIEW ALL DATA USER & SEARCH BY FILTER ROLE
    @GetMapping("/user/viewall")
    public String listUser(@RequestParam(name = "role", required = false) String role, Model model) {
        User loggedInUser = authenticationService.getLoggedInUser();
        List<User> listUser;

        if (role != null && !role.isEmpty()) {
            listUser = userRepository.findByRoleContainingIgnoreCase(role);
        } else {
            listUser = userRepository.findAll();
        }

        model.addAttribute("listUser", listUser);
        model.addAttribute("user", loggedInUser);
        return "user/viewall-user";
    }
 
    // UPDATE di page View All User 
    @GetMapping("user/update/{username}")
    public String formUpdateUser(@PathVariable("username") String username, Model model) {
        User currUser = userRepository.findByUsername(username);
        model.addAttribute("currUser", currUser);

        // Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "user/update-user";
    }

    @PostMapping("user/update/{username}")
    public String submitFormUpdateUser(@ModelAttribute User user, Model model) {
        //TODO: process POST request
        User updateUser = userService.updateUser(user);
        model.addAttribute("user_id", updateUser.getUser_id());
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);
        return "user/success-update-user";
    }

    // DELETE USER 
        //DELETE USER
    @GetMapping("user/delete/{username}")
    public String deleteUser(@PathVariable("username") String username, RedirectAttributes redirectAttributes, Model model) {
        User currUser = userRepository.findByUsername(username);
        
        // Menghapus User
        userService.deleteUser(currUser);

        // Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        //model.addAttribute("user_id", klien.getNamaKlien()); 
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        return "redirect:/user/viewall";
    }    
}