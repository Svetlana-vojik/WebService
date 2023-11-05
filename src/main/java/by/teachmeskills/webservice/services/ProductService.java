package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.ProductDto;

import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> findProducts(String searchWord);

    List<ProductDto> getProductByCategoryId(int id);

    ProductDto createProduct(ProductDto productDto);

    void deleteProduct(int id);

    ProductDto updateProduct(ProductDto productDto);

    ProductDto getProductById(int id);
}