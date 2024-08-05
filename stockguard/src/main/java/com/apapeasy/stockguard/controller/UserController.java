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
        // Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        // Menampilkan Modal Notifikasi
        List<Notifikasi> listNotifikasi = notifikasiService.getAllNotifikasi();
        if (listNotifikasi == null){
            listNotifikasi = new ArrayList<>();
        }
        model.addAttribute("listNotifikasi", listNotifikasi);
        model.addAttribute("jumlahNotifikasi", listNotifikasi.size());
        List<Item> items = itemService.getItemsCloseToExpiration();

        // Menampilkan Chart 10 data teratas
        items = items.stream()
                .limit(10)
                .collect(Collectors.toList());

        // Mengirim ke Model Attributenya dalam bentuk List of String tiap itemNames & Integer tiap Item Stocks
        // Jadi bukan seperti ini => model.addAttribute("items", items);
        String[] itemNames = new String[items.size()];
        Integer[] itemStocks = new Integer[items.size()];
        for (int i = 0; i < items.size(); i++) {
            itemNames[i] = items.get(i).getNamaItem();
            itemStocks[i] = items.get(i).getJumlahStok();
        }
        model.addAttribute("itemNames", itemNames);
        model.addAttribute("itemStocks", itemStocks);
        for(int i =0;i<items.size();i++){
            System.out.println("--> "+itemNames[i]+"------"+ itemStocks[i]);
        }

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

        // Cek validasi username & password
        if (userData != null && user.getPassword().equals(userData.getPassword())) {
            // Autentikasi
            authenticationService.addLoggedInUser(userData);
            User loggedInUser = authenticationService.getLoggedInUser();
            model.addAttribute("user", loggedInUser);

            //Redirect ke Home
            return "redirect:/";
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
        // Autentikasi
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

        // Autentikasi
        User user = authenticationService.getLoggedInUser();
        model.addAttribute("user", user);
        return "register";
    }
 
    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("userRegister") @Valid User userRegister, BindingResult bindingResult, Model model){
        // Validasi kesalahan pada Form
        if (bindingResult.hasErrors()) {
            model.addAttribute("userRegister", userRegister);
            model.addAttribute("errors", bindingResult.getAllErrors());

            // Autentikasi
            User user = authenticationService.getLoggedInUser();
            model.addAttribute("user", user);
            return "register";
        }

        // Cek username & email
        if (userService.existsByUsername(userRegister.getUsername()) || userService.existsByEmail(userRegister.getEmail())) {
            // Jika ada, tambahkan pesan error
            model.addAttribute("duplicateError", "Username atau email sudah terdaftar.");

            // Autentikasi
            User user = authenticationService.getLoggedInUser();
            model.addAttribute("userRegister", userRegister);
            model.addAttribute("user", user);
            return "register";
        }
        // Autentikasi
        User user = authenticationService.getLoggedInUser();
        model.addAttribute("user", user);

        // Add new user
        userService.registerUser(userRegister);

        //Redirect ke Home
        return "redirect:/";
    }

    @GetMapping("/user/viewall")
    public String listUser(@RequestParam(name = "role", required = false) String role, Model model) {
        User loggedInUser = authenticationService.getLoggedInUser();

        // View All user & based on Filter Role
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
 
    @GetMapping("user/update/{username}")
    public String formUpdateUser(@PathVariable("username") String username, Model model) {
        // Mendapatkan user yang akan diupdate
        User currUser = userRepository.findByUsername(username);
        model.addAttribute("currUser", currUser);

        // Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "user/update-user";
    }

    @PostMapping("user/update/{username}")
    public String submitFormUpdateUser(@ModelAttribute User user, Model model) {
        // Update user
        User updateUser = userService.updateUser(user);
        model.addAttribute("user_id", updateUser.getUser_id());

        // Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "user/success-update-user";
    }

    @GetMapping("user/delete/{username}")
    public String deleteUser(@PathVariable("username") String username, RedirectAttributes redirectAttributes, Model model) {
        // Mendapatkan user yang akan dihapus
        User currUser = userRepository.findByUsername(username);
        
        // Delete User
        userService.deleteUser(currUser);

        // Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully.");
        return "redirect:/user/viewall";
    }    
}