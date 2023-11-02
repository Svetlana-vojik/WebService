package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.converters.OrderConverter;
import by.teachmeskills.webservice.converters.ProductConverter;
import by.teachmeskills.webservice.converters.UserConverter;
import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.dto.UserDto;
import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.entities.User;

import by.teachmeskills.webservice.exceptions.CartIsEmptyException;
import by.teachmeskills.webservice.repositories.OrderRepository;
import by.teachmeskills.webservice.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final ProductConverter productConverter;
    private final OrderConverter orderConverter;
    private final OrderRepository orderRepository;

    public OrderDto create(UserDto userDto, CartDto cartDto) throws CartIsEmptyException {
        if (cartDto.getProducts() == null) {
            throw new CartIsEmptyException("Cart is empty");
        }
        Order order = Order.builder()
                .price(cartDto.getTotalPrice())
                .orderDate(LocalDate.now())
                .user(userConverter.fromDto(userDto))
                .productList(cartDto.getProducts().stream().map(productConverter::fromDto).toList())
                .build();
        order = orderRepository.createOrUpdateOrder(order);
        OrderDto orderDto = orderConverter.toDto(order);
        userDto.getOrders().add(orderDto);
        userRepository.createOrUpdateUser(userConverter.fromDto(userDto));
        cartDto.clear();
        cartDto.setTotalPrice(0);
        return orderDto;
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        Order order = Optional.ofNullable(orderRepository.findById(orderDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", orderDto.getId())));
        User user = userRepository.findById(orderDto.getUserId());
        order.setUser(user);
        order.setProductList(orderDto.getProductList().stream().map(productConverter::fromDto).toList());
        order.setPrice(order.getPrice());
        orderRepository.createOrUpdateOrder(order);
        return orderDto;
    }

    public void deleteOrder(int id) {
        orderRepository.delete(id);
    }

    public OrderDto getOrderById(int id) {
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", id)));
        return orderConverter.toDto(order);
    }

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

    public List<ProductDto> getProductByOrderId(int id) {
        Order order = Optional.ofNullable(orderRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Order with id %d not found", id)));
        return order.getProductList().stream().map(productConverter::toDto).toList();
    }
}