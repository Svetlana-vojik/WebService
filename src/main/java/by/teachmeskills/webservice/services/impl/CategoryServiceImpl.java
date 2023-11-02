package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.CategoryConverter;
import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.getAllCategories().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = Optional.ofNullable(categoryRepository.findNameById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id %d not found", id)));
        return categoryConverter.toDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryConverter.fromDto(categoryDto);
        category = categoryRepository.createOrUpdateCategory(category);
        return categoryConverter.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = Optional.ofNullable(categoryRepository.findNameById(categoryDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id %d not found", categoryDto.getId())));
        category.setName(categoryDto.getName());
        category.setRating(categoryDto.getRating());
        return categoryConverter.toDto(categoryRepository.createOrUpdateCategory(category));
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.delete(id);
    }
}