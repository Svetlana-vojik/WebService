package by.teachmeskills.webservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SearchParamsDto {
    private String searchKey;
    private Integer priceFrom;
    private Integer priceTo;
    private String categoryName;
}