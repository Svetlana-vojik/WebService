package by.teachmeskills.webservice.services.impl;

import by.teachmeskills.webservice.converters.CategoryConverter;
import by.teachmeskills.webservice.dto.CategoryDto;
import by.teachmeskills.webservice.entities.Category;
import by.teachmeskills.webservice.repositories.CategoryRepository;
import by.teachmeskills.webservice.services.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.getAllCategories().stream().map(categoryConverter::toDto).toList();
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = Optional.ofNullable(categoryRepository.findNameById(id))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id %d not found", id)));
        return categoryConverter.toDto(category);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryConverter.fromDto(categoryDto);
        category = categoryRepository.createOrUpdateCategory(category);
        return categoryConverter.toDto(category);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Category category = Optional.ofNullable(categoryRepository.findNameById(categoryDto.getId()))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with id %d not found", categoryDto.getId())));
        category.setName(categoryDto.getName());
        category.setRating(categoryDto.getRating());
        return categoryConverter.toDto(categoryRepository.createOrUpdateCategory(category));
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.delete(id);
    }

    @Override
    public List<CategoryDto> importCategoriesFromCsv(MultipartFile file) {
        List<CategoryDto> csvCategories = parseCsv(file);
        List<Category> categories = Optional.ofNullable(csvCategories)
                .map(list -> list.stream()
                        .map(categoryConverter::fromDto)
                        .toList())
                .orElse(null);
        if (Optional.ofNullable(categories).isPresent()) {
            categories.forEach(categoryRepository::createOrUpdateCategory);
            return categories.stream().map(categoryConverter::toDto).toList();
        }
        return Collections.emptyList();
    }

    @Override
    public void exportCategoriesToCsv(HttpServletResponse response) throws IOException, CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {
        List<CategoryDto> categories = categoryRepository.getAllCategories().stream().map(categoryConverter::toDto).toList();
        try (Writer writer = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<CategoryDto> statefulBeanToCsv = new StatefulBeanToCsvBuilder<CategoryDto>(writer).withSeparator(';').build();
            response.setContentType("text/csv");
            response.addHeader("Content-Disposition", "attachment; filename=" + "categories.csv");
            statefulBeanToCsv.write(categories);
        }
    }

    private List<CategoryDto> parseCsv(MultipartFile file) {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<CategoryDto> csvToBean = new CsvToBeanBuilder<CategoryDto>(reader)
                        .withType(CategoryDto.class)
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