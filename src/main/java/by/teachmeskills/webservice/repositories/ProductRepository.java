package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Product;

import java.util.List;

public interface ProductRepository {
    Product createOrUpdateProduct(Product product);

    void delete(int id);

    Product findById(int id);

    List<Product> findByCategoryId(int id);

    List<Product> findProductsByWord(String searchWord);

    List<Product> findAll();
}