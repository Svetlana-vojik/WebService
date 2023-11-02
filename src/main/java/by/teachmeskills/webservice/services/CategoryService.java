package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.exceptions.ExportFIleException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CategoryService  {
    List<CategoryDto> getAllCategories();

    CategoryDto getCategoryById(int id);

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(CategoryDto categoryDto);

    void deleteCategory(int id);

    List<CategoryDto> importCategoriesFromCsv(MultipartFile file);

    void exportCategoriesToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException;
}