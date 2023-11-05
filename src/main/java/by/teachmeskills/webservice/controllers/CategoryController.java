package by.teachmeskills.webservice.controllers;

import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.services.CategoryService;
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
@RequestMapping("/categories")
@AllArgsConstructor
@Validated
@Tag(name = "category", description = "Category Endpoint")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
            summary = "Find all categories",
            description = "Find all existed categories in Shop",
            tags = {"category"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All categories were found",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Categories not found"
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @Operation(
            summary = "Find certain category",
            description = "Find certain existed category in Shop by its id",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was found by its id",
                    content = @Content(schema = @Schema(contentSchema = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Category not fount"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@Parameter(required = true, description = "Category ID") @PathVariable int id) {
        return Optional.ofNullable(categoryService.getCategoryById(id))
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Create category",
            description = "Create new category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Category was created",
                    content = @Content(schema = @Schema(contentSchema = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category not created"
            )
    })
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update category",
            description = "Update existed category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was updated",
                    content = @Content(schema = @Schema(contentSchema = CategoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category not updated"
            )
    })
    @PutMapping
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete category",
            description = "Delete existed category",
            tags = {"category"})
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Category was deleted"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Category not deleted"
            )
    })
    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable @Positive int id) {
        categoryService.deleteCategory(id);
    }

    @Operation(
            summary = "Save new categories",
            description = "Add new categories from csv file and persist to database",
            tags = {"category"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All categories were added",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categories not added"
                    )
            }
    )
    @PostMapping("/csv/import")
    public ResponseEntity<List<CategoryDto>> importCategoriesFromCsv(@RequestParam("file") MultipartFile file) {
        return new ResponseEntity<>(categoryService.importCategoriesFromCsv(file), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Export all categories",
            description = "Export all existed categories from Shop database  to csv file",
            tags = {"category"}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All categories were exported",
                            content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Categories not exported"
                    )
            }
    )
    @GetMapping("/csv/export")
    public void exportCategoriesToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        categoryService.exportCategoriesToCsv(response);
    }
}