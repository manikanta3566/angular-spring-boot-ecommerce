package com.project.ecommerce.service;

import com.project.ecommerce.dto.CategoryDto;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    Map<String,String> createCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();
}
