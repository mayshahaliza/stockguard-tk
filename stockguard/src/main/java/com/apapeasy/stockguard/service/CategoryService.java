package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    void addCategory (Category category);
    Category getCategoryById(Integer categoryId);
    Category updateCategory(Category category);
    void deleteCategory(Category category);
}
