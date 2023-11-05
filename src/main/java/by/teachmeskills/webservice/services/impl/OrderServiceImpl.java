package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.OrderConverter;
import by.teachmeskills.webservice.converters.ProductConverter;
import by.teachmeskills.webservice.converters.UserConverter;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.repositories.OrderRepository;
import by.teachmeskills.webservice.repositories.UserRepository;
import by.teachmeskills.webservice.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final ProductConverter productConverter;
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;

 @Override
 public OrderDto updateOrder(OrderDto orderDto) {
     Order order = Optional.ofNullable(orderRepository.findById(orderDto.getId()))
             .orElseThrow(() -> new EntityNotFoundException(String.format("Заказа с id %d не найдено.", orderDto.getId())));
     order.setOrderDate(orderDto.getOrderDate());
     order.setPrice(orderDto.getPrice());
     return orderConverter.toDto(orderRepository.createOrUpdateOrder(order));
 }

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        Order order = orderConverter.fromDto(orderDto);
        order = orderRepository.createOrUpdateOrder(order);
        return orderConverter.toDto(order);
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.delete(id);
    }

    @Override
    public OrderDto getOrderById(int id) {
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", id)));
        return orderConverter.toDto(order);
    }

    @Override
    public List<OrderDto> getOrdersByUserId(int id) {
        if (userRepository.findById(id) == null) {
            throw new EntityNotFoundException(String.format("User with id %d not found", id));
        }
        List<OrderDto> list = orderRepository.findByUserId(id).stream().map(orderConverter::toDto).toList();
        if (list.size() == 0) {
            throw new EntityNotFoundException(String.format("User with id %d dont have orders", id));
        }
        return list;
    }

    @Override
    public List<ProductDto> getProductByOrderId(int id) {
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", id)));
        return order.getProductList().stream().map(productConverter::toDto).toList();
    }
    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(orderConverter::toDto).toList();
    }

}