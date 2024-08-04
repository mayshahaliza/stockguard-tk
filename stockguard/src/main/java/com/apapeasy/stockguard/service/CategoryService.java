package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Category;
import com.apapeasy.stockguard.model.Item;

import java.time.LocalDate;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();
    void addCategory (Category category);
    Category getCategoryById(Integer categoryId);
    Category updateCategory(Category category);
    void deleteCategory(Category category);
    //public Category getCategoryById(Integer categoryId);

}
