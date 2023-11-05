package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.services.ProductService;
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
@RequestMapping("/products")
@AllArgsConstructor
@Validated
@Tag(name = "product", description = "Product Endpoint")
public class ProductController {
    private final ProductService productService;

    @Operation(
            summary = "Find all products",
            description = "Find all existed products in Shop",
            tags = {"product"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All products were found",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Products not found"
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @Operation(
            summary = "Create product",
            description = "Create new product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Product was created",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product not created"
            )
    })
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.createProduct(productDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update product",
            description = "Update existed product",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was updated",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Product not updated"
            )
    })
    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid ProductDto productDto) {
        return new ResponseEntity<>(productService.updateProduct(productDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete product",
            description = "Delete existed product",
            tags = {"product"})
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
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable @Positive int id) {
        productService.deleteProduct(id);
    }

    @Operation(
            summary = "Find certain product",
            description = "Find certain existed product in Shop by its id",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Product was found by its id",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Product not fount - forbidden operation"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable @Positive int id) {
        return Optional.ofNullable(productService.getProductById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Find category products",
            description = "Find all category products in Shop",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All products for category found",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Products not fount - forbidden operation"
            )
    })
    @GetMapping("/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductByCategoryId(@PathVariable @Positive int id) {
        return new ResponseEntity<>(productService.getProductByCategoryId(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Find products",
            description = "Find all products by search parameter in Shop",
            tags = {"product"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Products by search parameter found",
                    content = @Content(schema = @Schema(contentSchema = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Products not fount - forbidden operation"
            )
    })
    @GetMapping("/search/{searchWord}")
    public ResponseEntity<List<ProductDto>> findProducts(@PathVariable String searchWord) {
        return new ResponseEntity<>(productService.findProducts(searchWord), HttpStatus.OK);
    }

    @Operation(
            summary = "Import new products",
            description = "Add new products to Shop database from csv file",
            tags = {"product"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All products were added",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Products not added - server error"
                    )
            }
    )
    @PostMapping("/csv/import")
    public ResponseEntity<List<ProductDto>> importProductsFromCsv(@RequestParam("file") MultipartFile file) throws Exception {
        return new ResponseEntity<>(productService.importProductsFromCsv(file), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Export all products",
            description = "Export all existed products from Shop database  to csv file",
            tags = {"product"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All products were exported",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProductDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Products not exported"
                    )
            }
    )
    @GetMapping("/csv/export/{categoryId}")
    public void exportProductsToCsv(HttpServletResponse response, @Parameter(required = true, description = "Category ID")
    @PathVariable int categoryId) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        productService.exportProductsToCsv(response, categoryId);
    }
}