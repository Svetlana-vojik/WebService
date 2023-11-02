package by.teachmeskills.webservice.repositories;

import by.teachmeskills.webservice.entities.Order;

import java.util.List;

public interface OrderRepository {

    Order createOrUpdateOrder(Order order);

    Order findById(int id);

    void delete(int id);

    List<Order> findAll();

    List<Order> findByUserId(int id);
}