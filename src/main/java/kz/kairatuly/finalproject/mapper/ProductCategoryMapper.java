package kz.kairatuly.finalproject.mapper;

import kz.kairatuly.finalproject.dto.ProductCategoryDto;
import kz.kairatuly.finalproject.entities.ProductCategory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryDto toDto(ProductCategory category);

    ProductCategory toEntity(ProductCategoryDto categoryDto);

    List<ProductCategoryDto> toDtoList(List<ProductCategory> categories);
}

