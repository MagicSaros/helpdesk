package com.epam.repository;

import com.epam.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category addCategory(Category category);

    Optional<Category> getCategoryByName(String name);
}
