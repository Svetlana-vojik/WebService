package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.OrderDto;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.services.OrderService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Validated
@Tag(name = "order", description = "Order Endpoint")
public class OrderController {

    private final OrderService orderService;
    @Operation(
            summary = "Create order",
            description = "Create new order",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Order was created",
                    content = @Content(schema = @Schema(contentSchema = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order not created"
            )
    })
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Update order",
            description = "Update existed order",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was updated",
                    content = @Content(schema = @Schema(contentSchema = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order not updated"
            )
    })
    @PutMapping
    public ResponseEntity<OrderDto> updateOrder(@RequestBody @Valid OrderDto orderDto) {
        return new ResponseEntity<>(orderService.updateOrder(orderDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Find all orders",
            description = "Find all existed orders in Shop",
            tags = {"order"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All orders were found",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Orders not found"
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }
    @Operation(
            summary = "Delete order",
            description = "Delete existed order",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Order not deleted"
            )
    })
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable @Positive int id) {
        orderService.deleteOrder(id);
    }

    @Operation(
            summary = "Find certain order",
            description = "Find certain existed order in Shop by its id",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Order was found by its id",
                    content = @Content(schema = @Schema(contentSchema = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Order not fount - forbidden operation"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable @Positive int id) {
        return Optional.ofNullable(orderService.getOrderById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(
            summary = "Find user orders",
            description = "Find all user orders in Shop",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All orders for user found",
                    content = @Content(schema = @Schema(contentSchema = OrderDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Orders not fount - forbidden operation"
            )
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable @Positive int id) {
        return Optional.ofNullable(orderService.getOrdersByUserId(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(
            summary = "Find order products",
            description = "Find all order products in Shop",
            tags = {"order"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All products for order found",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Products not fount - forbidden operation"
            )
    })
    @GetMapping("/products/{id}")
    public ResponseEntity<List<ProductDto>> getProductByOrderId(@PathVariable @Positive int id) {
        return Optional.ofNullable(orderService.getProductByOrderId(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Operation(
            summary = "Import new orders",
            description = "Add new orders to Shop database from csv file",
            tags = {"order"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All orders were added",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Orders not added - server error"
                    )
            }
    )
    @PostMapping("/csv/import")
    public ResponseEntity<List<OrderDto>> importOrdersFromCsv(@RequestParam("file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(orderService.importOrdersFromCsv(file), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Export all user orders",
            description = "Export all existed user orders from Shop database  to csv file",
            tags = {"order"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All user orders were exported",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Orders not exported"
                    )
            }
    )
    @GetMapping("/csv/export/{userId}")
    public void exportOrdersToCsv(HttpServletResponse response, @Parameter(required = true, description = "User ID")
    @PathVariable int userId) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        orderService.exportOrdersToCsv(response, userId);
    }
}