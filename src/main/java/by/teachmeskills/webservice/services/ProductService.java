package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.dto.ProductDto;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();

    List<ProductDto> findProducts(String searchWord);

    List<ProductDto> getProductByCategoryId(int id);

    ProductDto createProduct(ProductDto productDto);

    void deleteProduct(int id);

    ProductDto updateProduct(ProductDto productDto);

    ProductDto getProductById(int id);

    List<ProductDto> importProductsFromCsv(MultipartFile file);

    void exportProductsToCsv(HttpServletResponse response, int categoryId) throws  CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException;
}