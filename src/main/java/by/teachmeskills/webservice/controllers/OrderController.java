package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.services.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid OrderDto orderDto) {
        return new ResponseEntity<>(orderService.updateOrder(orderDto), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable @Positive int id) {
        orderService.deleteOrder(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable @Positive int id) {
        return Optional.ofNullable(orderService.getOrderById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable @Positive int id) {
        return Optional.ofNullable(orderService.getOrdersByUserId(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductDto>> getProductByOrderId(@PathVariable @Positive int id) {
        return Optional.ofNullable(orderService.getProductByOrderId(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}