package kz.kairatuly.finalproject.services;

import kz.kairatuly.finalproject.dto.ProductDto;
import kz.kairatuly.finalproject.entities.Product;
import kz.kairatuly.finalproject.entities.ProductCategory;
import kz.kairatuly.finalproject.mapper.ProductMapper;
import kz.kairatuly.finalproject.repositories.ProductCategoryRepository;
import kz.kairatuly.finalproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private final Random random;

    public ProductService() {
        this.random = new Random();
    }

    public ProductDto createProduct(ProductDto product) {
        Product productEntity = productMapper.toEntity(product);
        return productMapper.toDto(productRepository.save(productEntity));
    }

    public List<ProductDto> getAllProduct() {
        return productMapper.toDtoList(productRepository.findAll());
    }

    public ProductDto getProduct(Long id) {
        return productMapper.toDto(productRepository.findById(id).orElseThrow());
    }

    public ProductDto updateProduct(ProductDto productDto) {
        Product productEntity = productMapper.toEntity(productDto);
        productEntity.setName(productDto.getName());
        productEntity.setDescription(productDto.getDescription());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setAmount(productDto.getAmount());
        productEntity.setPromoCode(UUID.randomUUID().toString());
        return productMapper.toDto(productRepository.save(productEntity));
    }

    public List<ProductDto> getProductByCategories(Long categoryId) {
        ProductCategory category = productCategoryRepository.findById(categoryId).orElseThrow();
        List<Product> products = productRepository.findByCategories(category);
        return productMapper.toDtoList(products);
    }

    public List<ProductDto> getAllProductRandom() {
        List<Product> products = productRepository.findAll();
        Collections.shuffle(products, random);
        return productMapper.toDtoList(products);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
