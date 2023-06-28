package kz.kairatuly.finalproject.mapper;

import kz.kairatuly.finalproject.dto.ProductDto;
import kz.kairatuly.finalproject.entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    List<ProductDto> toDtoList(List<Product> products);
}
