package com.epam.service;

import com.epam.entity.Category;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    Category addCategory(Category category);

    Category getCategoryByName(String name);
}
