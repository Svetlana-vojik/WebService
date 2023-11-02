package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.converters.ProductConverter;
import by.teachmeskills.webservice.dto.CartDto;

import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    public CartDto addProduct(int id, CartDto cartDto) {
        Product product = Optional.ofNullable(productRepository.findById(id)).
                orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: %d not found", id)));
        cartDto.addProduct(productConverter.toDto(product));
        return cartDto;
    }

    public CartDto removeProduct(int id, CartDto cartDto) {
        Product product = Optional.ofNullable(productRepository.findById(id)).
                orElseThrow(() -> new EntityNotFoundException(String.format("Product with id: %d not found", id)));
        cartDto.removeProduct(productConverter.toDto(product));
        return cartDto;
    }

    public CartDto clear(CartDto cartDto) {
        cartDto.clear();
        return cartDto;
    }
}