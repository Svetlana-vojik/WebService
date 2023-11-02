package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Category;

import java.util.List;

public interface CategoryRepository {

    Category createOrUpdateCategory(Category category);

    void delete(int id);

    List<Category> getAllCategories();

    Category findNameById(int id);
}