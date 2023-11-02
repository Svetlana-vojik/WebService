package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;


import java.util.List;

public interface OrderService {

    OrderDto updateOrder(OrderDto orderDto);

    OrderDto createOrder(OrderDto orderDto);

    void deleteOrder(int id);

    OrderDto getOrderById(int id);

    List<OrderDto> getOrdersByUserId(int id);

    List<ProductDto> getProductByOrderId(int id);

    List<OrderDto> getAllOrders();
}