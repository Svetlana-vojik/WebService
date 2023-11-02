package by.teachmeskills.webservice.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto extends BaseDto {

    @PastOrPresent
    private LocalDate orderDate;

    @NotNull(message = "Поле должно быть заполнено!")
    @Digits(integer = 5, fraction = 2)
    private int price;

    @NotNull(message = "Поле должно быть заполнено!")
    private int userId;

    private List<ProductDto> productList;
}