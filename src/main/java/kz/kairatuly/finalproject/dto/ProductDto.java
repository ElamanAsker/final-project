package kz.kairatuly.finalproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private int price;
    private int amount;
    private List<ProductCategoryDto> categories;
    private List<PicturesDto> pictures;
}
