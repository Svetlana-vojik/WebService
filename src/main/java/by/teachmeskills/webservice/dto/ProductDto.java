package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends BaseDto {

    @Size(min = 1, max = 40, message = "Пустое или длиннее 40 символов")
    private String name;
    private String description;
    @Digits(integer = 6, fraction = 0)
    private int price;
    @Digits(integer = 5, fraction = 0)
    private int categoryId;
    private String imagePath;
}