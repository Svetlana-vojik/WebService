package by.teachmeskills.webservice.converters;

import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.entities.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CategoryConverter {
    private final ProductConverter productConverter;

    public CategoryDto toDto(Category category) {
        return Optional.ofNullable(category).map(c -> CategoryDto.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .imagePath(c.getImagePath())
                        .rating(c.getRating())
                        .build())
                .orElse(null);
    }

    public Category fromDto(CategoryDto categoryDto) {
        return Optional.ofNullable(categoryDto).map(c -> Category.builder()
                        .name(c.getName())
                        .imagePath(c.getImagePath())
                        .rating(c.getRating())
                        .build())
                .orElse(null);
    }
}