package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.CategoryDto;
import com.epam.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter implements DtoConverter<Category, CategoryDto> {

    @Override
    public CategoryDto fromEntityToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public Category fromDtoToEntity(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
