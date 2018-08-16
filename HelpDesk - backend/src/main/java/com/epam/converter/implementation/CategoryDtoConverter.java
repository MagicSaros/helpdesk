package com.epam.converter.implementation;

import com.epam.converter.DtoConverter;
import com.epam.dto.CategoryDto;
import com.epam.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter implements DtoConverter<Category, CategoryDto> {

    @Override
    public CategoryDto fromEntityToDto(final Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    @Override
    public Category fromDtoToEntity(final CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        return category;
    }
}
