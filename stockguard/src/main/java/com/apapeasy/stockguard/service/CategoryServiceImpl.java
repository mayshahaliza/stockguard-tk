package com.apapeasy.stockguard.service;

import com.apapeasy.stockguard.model.Category;
import com.apapeasy.stockguard.repository.CategoryDb;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDb categoryDb;

    public List<Category> getAllCategory(){
        return categoryDb.findAll();
    }

    public void addCategory(Category category){
        categoryDb.save(category);
    }

    public Category updateCategory(Category category) {
        categoryDb.save(category);
        return category;
    }

    public void deleteCategory(Category category) {
        categoryDb.delete(category);
    }
    
    public Category getCategoryById(Integer categoryId) {
        return categoryDb.findById(categoryId).get();
    }
}
