package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.CategoryDto;

import java.util.List;

public interface CategoryService  {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(int id);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(int id);
}