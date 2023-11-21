package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.CartDto;
import by.teachmeskills.webservice.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cart")
@Validated
@Tag(name = "cart", description = "Cart endpoints")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Operation(
            summary = "Add product",
            description = "Add product to cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was added"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    @PostMapping("/addProduct/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<CartDto> addProduct(@PathVariable @Positive int id, @RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.addProduct(id, cartDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete product",
            description = "Delete product from shopping cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product not deleted"
            )
    })
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<CartDto> removeProduct(@PathVariable @Positive int id, @RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.removeProduct(id, cartDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete all products",
            description = "Delete all products from cart",
            tags = {"cart"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products were deleted from cart"
            )
    })
    @DeleteMapping("/clear")
    public ResponseEntity<CartDto> clear(@RequestBody CartDto cartDto) {
        return new ResponseEntity<>(cartService.clear(cartDto), HttpStatus.OK);
    }
}