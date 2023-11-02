package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.ProductConverter;
import by.teachmeskills.webservice.dto.ProductDto;
import by.teachmeskills.webservice.entities.Product;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.repositories.ProductRepository;
import by.teachmeskills.webservice.services.ProductService;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.hibernate.query.sqm.ParsingException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductBySearch(String searchWord) {
        return productRepository.findProductsByWord(searchWord).stream().map(productConverter::toDto).toList();
    }

    @Override
    public List<ProductDto> getProductByCategoryId(int id) {
        if (categoryRepository.findNameById(id) == null) {
            throw new EntityNotFoundException(String.format("Category with id %d not found", id));
        }
        List<ProductDto> list = productRepository.findByCategoryId(id).stream().map(productConverter::toDto).toList();
        if (list.size() == 0) {
            throw new EntityNotFoundException(String.format("Category with id %d dont have products", id));
        }
        return list;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productConverter.fromDto(productDto);
        product = productRepository.createOrUpdateProduct(product);
        return productConverter.toDto(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.delete(id);
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = Optional.ofNullable(productRepository.findById(productDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %d not found", productDto.getId())));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        productDto.setCategoryId(productDto.getCategoryId());
        product.setImagePath(productDto.getImagePath());
        return productConverter.toDto(productRepository.createOrUpdateProduct(product));
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = Optional.ofNullable(productRepository.findById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %d not found", id)));
        return productConverter.toDto(product);
    }
    @Override
    public List<ProductDto> importProductsFromCsv(MultipartFile file) {
        List<ProductDto> csvProducts = parseCsv(file);
        List<Product> orders = Optional.ofNullable(csvProducts)
                .map(list -> list.stream()
                        .map(productConverter::fromDto)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(orders).isPresent()) {
            orders.forEach(productRepository::createOrUpdateProduct);
            return orders.stream().map(productConverter::toDto).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public void exportProductsToCsv(HttpServletResponse response, int id) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException {
        List<ProductDto> products = productRepository.findByCategoryId(id).stream().map(productConverter::toDto).toList();
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<ProductDto> statefulBeanToCsv = new StatefulBeanToCsvBuilder<ProductDto>(writer).withSeparator(';').build();
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=" + "products.csv");
            statefulBeanToCsv.write(products);
        }

    }

    private List<ProductDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<ProductDto> csvToBean = new CsvToBeanBuilder<ProductDto>(reader)
                        .withType(ProductDto.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .withSeparator(',')
                        .build();

                return csvToBean.parse();
            } catch (Exception ex) {
                throw new ParsingException(String.format("Ошибка во время преобразования данных: %s", ex.getMessage()));
            }
        }
        return Collections.emptyList();
    }

}