package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.ProductConverter;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.repositories.ProductRepository;
import by.teachmeskills.webservice.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductBySearch(String searchWord) {
        return productRepository.findProductsByWord(searchWord).stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductByCategoryId(int id) {
        if (categoryRepository.findNameById(id) == null) {
            throw new EntityNotFoundException(String.format("Category with id %d not found", id));
        }
        List<ProductDto> list = productRepository.findByCategoryId(id).stream().map(productConverter::toDto).toList();
        if (list.size() == 0) {
            throw new EntityNotFoundException(String.format("Category with id %d dont have products", id));
        }
        return list;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productConverter.fromDto(productDto);
        product = productRepository.createOrUpdateProduct(product);
        return productConverter.toDto(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.delete(id);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = Optional.ofNullable(productRepository.findById(productDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %d not found", productDto.getId())));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productDto.setCategoryId(productDto.getCategoryId());
        product.setImagePath(productDto.getImagePath());
        return productConverter.toDto(productRepository.createOrUpdateProduct(product));
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = Optional.ofNullable(productRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %d not found", id)));
        return productConverter.toDto(product);
    }
}