package by.teachmeskills.webservice.services;


import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.exceptions.CartIsEmptyException;

import java.util.List;

public interface OrderService {
    OrderDto create(UserDto userDto, CartDto cartDto) throws CartIsEmptyException;

    OrderDto updateOrder(OrderDto orderDto);

    void deleteOrder(int id);

    OrderDto getOrderById(int id);

    List<OrderDto> getOrdersByUserId(int id);

    List<ProductDto> getProductByOrderId(int id);
}