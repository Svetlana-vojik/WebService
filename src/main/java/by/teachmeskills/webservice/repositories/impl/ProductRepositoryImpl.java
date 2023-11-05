package by.teachmeskills.webservice.repositories.impl;

import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Product createOrUpdateProduct(Product product) {
        return entityManager.merge(product);
    }

    @Override
    public void delete(int id) {
        Product product = Optional.ofNullable(entityManager.find(Product.class, id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %d not found", id)));
        entityManager.remove(product);
    }

    @Override
    public Product findById(int id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public List<Product> findByCategoryId(int id) {
        TypedQuery<Product> query = entityManager.createQuery("select p from Product p where p.category.id=:categoryId", Product.class);
        query.setParameter("categoryId", id);
        return query.getResultList();
    }

    @Override
    public List<Product> findProducts(String searchWord) {
        TypedQuery<Product> query = entityManager.createQuery("from Product where name like :search or description like :search", Product.class);
        query.setParameter("search", "%" + searchWord + "%");
        return query.getResultList();
    }

    @Override
    public List<Product> findAll() {
        return entityManager.createQuery("from Product", Product.class).getResultList();
    }
}