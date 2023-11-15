package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Product findById(int id);

    List<Product> findByCategoryId(int id);

    @Query(value = "select * from shop.products where name  = :parameter", nativeQuery = true)
    List<Product> findProducts(@Param("parameter") String parameter);

    List<Product> findAll();
}