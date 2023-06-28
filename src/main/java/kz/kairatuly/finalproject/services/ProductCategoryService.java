package kz.kairatuly.finalproject.services;

import kz.kairatuly.finalproject.dto.ProductCategoryDto;
import kz.kairatuly.finalproject.entities.ProductCategory;
import kz.kairatuly.finalproject.mapper.ProductCategoryMapper;
import kz.kairatuly.finalproject.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public ProductCategoryDto createCategory(ProductCategoryDto categoryDto) {
        ProductCategory categoryEntity = productCategoryMapper.toEntity(categoryDto);
        return productCategoryMapper.toDto(productCategoryRepository.save(categoryEntity));
    }

    public List<ProductCategoryDto> getAllCategories() {
        return productCategoryMapper.toDtoList(productCategoryRepository.findAll());
    }

    public ProductCategoryDto getCategory(Long id) {
        return productCategoryMapper.toDto(productCategoryRepository.findById(id).orElseThrow());
    }

    public ProductCategoryDto updateCategory(ProductCategoryDto categoryDto) {
        ProductCategory categoryEntity = productCategoryMapper.toEntity(categoryDto);
        return productCategoryMapper.toDto(productCategoryRepository.save(categoryEntity));
    }

    public void deleteCategory(Long id) {
        productCategoryRepository.deleteById(id);
    }

}
