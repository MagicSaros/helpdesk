package com.epam.service.implementation;

import com.epam.entity.Category;
import com.epam.exception.CategoryNotFoundException;
import com.epam.repository.CategoryRepository;
import com.epam.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.getCategoryById(id);
        return category
            .orElseThrow(() -> new CategoryNotFoundException("Category not found by passed id"));
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.addCategory(category);
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.getCategoryByName(name).orElseThrow(
            CategoryNotFoundException::new);
    }
}
