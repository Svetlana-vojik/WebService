package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findById(int id);

    List<Order> findAll();

    List<Order> findByUserId(int id);
}