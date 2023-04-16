package com.project.ecommerce.service.impl;

import com.project.ecommerce.Repository.CategoryRepository;
import com.project.ecommerce.dto.CategoryDto;
import com.project.ecommerce.entity.Category;
import com.project.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public Map<String, String> createCategory(CategoryDto categoryDto) {
        Category category=new Category();
        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return Map.of("categoryId",category.getId());
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category ->modelMapper.map(category,CategoryDto.class))
                .collect(Collectors.toList());
    }
}
