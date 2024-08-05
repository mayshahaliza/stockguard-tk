package com.apapeasy.stockguard.controller;

import com.apapeasy.stockguard.dto.CategoryRequestDTO;
import com.apapeasy.stockguard.model.Category;
import com.apapeasy.stockguard.model.User;
import com.apapeasy.stockguard.repository.CategoryDb;
import com.apapeasy.stockguard.service.AuthenticationService;
import com.apapeasy.stockguard.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDb categoryDb;

    @GetMapping("/category")
    public String viewAllCategory(Model model){
        List<Category> listCategory = categoryService.getAllCategory();
        model.addAttribute("listCategory", listCategory);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "category/viewall-category";
    }

    @GetMapping("/category/add")
    public String addCategory(Model model){
        CategoryRequestDTO dto = new CategoryRequestDTO();
        model.addAttribute("dto", dto);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "category/form-add-category";
    }

    @PostMapping("/category/add")
    public String addCategorySubmit(@ModelAttribute Category category, Model model){
        categoryService.addCategory(category);
        model.addAttribute("category_id", category.getCategory_id());

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "category/add-category";
    }

    @GetMapping("/category/update/{id}")
    public String formUpdateCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "category/form-update-category";
    }

    @PostMapping("/category/update/{id}")
    public String updateCategory(@ModelAttribute Category category, Model model) {
        Category updateCategory = categoryService.updateCategory(category);
        model.addAttribute("category_id", updateCategory.getCategory_id());

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        return "category/success-update-category";
    }

    @GetMapping("/category/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes, Model model) {
        Category category = categoryService.getCategoryById(id);   
        categoryService.deleteCategory(category);

        //Autentikasi
        User loggedInUser = authenticationService.getLoggedInUser();
        model.addAttribute("user", loggedInUser);

        redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully.");
        return "redirect:/category";
    }
}
