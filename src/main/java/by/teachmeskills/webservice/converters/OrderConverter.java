package by.teachmeskills.webservice.converters;

import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.entities.Order;
import by.teachmeskills.webservice.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OrderConverter {

    private final ProductConverter productConverter;
    private final UserRepository userRepository;

    public OrderDto toDto(Order order) {
        return Optional.ofNullable(order).map(o -> OrderDto.builder()
                        .id(o.getId())
                        .orderDate(o.getOrderDate())
                        .price(o.getPrice())
                        .userId(o.getUser().getId())
                        .productList(Optional.ofNullable(o.getProductList()).map(products -> products
                                .stream().map(productConverter::toDto).toList()).orElse(List.of()))
                        .build())
                .orElse(null);
    }

    public Order fromDto(OrderDto orderDto) {
        return Order.builder()
                .orderDate(orderDto.getOrderDate())
                .price(orderDto.getPrice())
                .user(userRepository.findById(orderDto.getUserId()))
                .build();
    }
}