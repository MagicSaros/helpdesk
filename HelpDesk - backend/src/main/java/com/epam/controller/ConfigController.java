package com.epam.controller;

import com.epam.converter.implementation.CategoryDtoConverter;
import com.epam.dto.CategoryDto;
import com.epam.exception.ApiError;
import com.epam.service.CategoryService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/config")
@CrossOrigin
@Api(value = "config", description = "Config API")
public class ConfigController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDtoConverter categoryDtoConverter;

    @GetMapping("/tickets/categories")
    @ApiOperation(value = "Get a list of available categories for ticket", response = CategoryDto.class, responseContainer = "List")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success", response = CategoryDto.class),
        @ApiResponse(code = 404, message = "Resource not found", response = ApiError.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ApiError.class)})
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoriesDto = categoryService
            .getAllCategories()
            .stream()
            .map(categoryDtoConverter::fromEntityToDto)
            .collect(Collectors.toList());
        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }
}
