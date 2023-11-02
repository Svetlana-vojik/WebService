package by.teachmeskills.webservice.repositories.impl;

import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@AllArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Category createOrUpdateCategory(Category category) {
        return entityManager.merge(category);
    }

    @Override
    public void delete(int id) {
        Category category = Optional.ofNullable(entityManager.find(Category.class, id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id %d not found", id)));
        entityManager.remove(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return entityManager.createQuery("from Category", Category.class).getResultList();
    }

    @Override
    public Category findNameById(int id) {
        return entityManager.find(Category.class, id);
    }
}