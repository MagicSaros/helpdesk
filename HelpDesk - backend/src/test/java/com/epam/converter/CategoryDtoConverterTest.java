package com.epam.converter;

import com.epam.converter.implementation.CategoryDtoConverter;
import com.epam.dto.CategoryDto;
import com.epam.entity.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CategoryDtoConverterTest {

    private Category category;

    private CategoryDto categoryDto;

    private CategoryDtoConverter categoryDtoConverter;

    @Before
    public void init() {
        categoryDtoConverter = new CategoryDtoConverter();

        category = new Category();
        category.setId(1);
        category.setName("Category");

        categoryDto = new CategoryDto();
        categoryDto.setId(1);
        categoryDto.setName("Category");
    }

    @Test
    public void fromEntityToDtoTest() {
        CategoryDto actual = categoryDtoConverter.fromEntityToDto(category);

        assertEquals(categoryDto.getId(), actual.getId());
        assertEquals(categoryDto.getName(), actual.getName());
    }

    @Test
    public void fromDtoToEntityTest() {
        Category actual = categoryDtoConverter.fromDtoToEntity(categoryDto);

        assertEquals(category.getId(), actual.getId());
        assertEquals(category.getName(), actual.getName());
    }
}
