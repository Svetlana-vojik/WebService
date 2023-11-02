package by.teachmeskills.webservice.converters;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductConverter {
    private final CategoryRepository categoryRepository;

    public ProductDto toDto(Product product) {
        return Optional.ofNullable(product).map(p -> ProductDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .categoryId(p.getCategory().getId())
                        .imagePath(p.getImagePath())
                        .build())
                .orElse(null);
    }

    public Product fromDto(ProductDto productDto) {
        return Optional.ofNullable(productDto).map(p -> Product.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .description(p.getDescription())
                        .price(p.getPrice())
                        .category(categoryRepository.findNameById(p.getCategoryId()))
                        .imagePath(p.getImagePath())
                        .build())
                .orElse(null);
    }
}