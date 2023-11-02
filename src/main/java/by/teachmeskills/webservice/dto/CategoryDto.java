package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto extends BaseDto {

    @NotNull(message = "имя категории не должно быть пустым")
    @Size(min = 1, max = 40, message = "имя категории пустое или длиннее 40 символов")
    private String name;
    private String imagePath;
    private int rating;
    private List<ProductDto> products;
}