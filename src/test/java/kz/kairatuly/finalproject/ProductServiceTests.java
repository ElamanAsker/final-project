package kz.kairatuly.finalproject;

import kz.kairatuly.finalproject.dto.ProductCategoryDto;
import kz.kairatuly.finalproject.dto.ProductDto;
import kz.kairatuly.finalproject.entities.Product;
import kz.kairatuly.finalproject.entities.ProductCategory;
import kz.kairatuly.finalproject.mapper.ProductMapper;
import kz.kairatuly.finalproject.repositories.ProductCategoryRepository;
import kz.kairatuly.finalproject.repositories.ProductRepository;
import kz.kairatuly.finalproject.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testProductDtoMapper() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(10L);
        productCategory.setName("Category");

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        Product product = new Product();
        product.setId(10L);
        product.setName("Product");
        product.setPrice(100);
        product.setAmount(25);
        product.setDescription("some desc");
        product.setCategories(productCategories);

        ProductDto productDto = productMapper.toDto(product);

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getId(), productDto.getId());
        Assertions.assertEquals(product.getName(), productDto.getName());
        Assertions.assertEquals(product.getPrice(), productDto.getPrice());
        Assertions.assertEquals(product.getAmount(), productDto.getAmount());
        Assertions.assertEquals(product.getDescription(), productDto.getDescription());

        Assertions.assertNotNull(productDto.getCategories());
        Assertions.assertEquals(product.getCategories().size(), productDto.getCategories().size());

        for (int i = 0; i < product.getCategories().size(); i++) {
            ProductCategory category = product.getCategories().get(i);
            ProductCategoryDto categoryDto = productDto.getCategories().get(i);
            Assertions.assertEquals(category.getId(), categoryDto.getId());
            Assertions.assertEquals(category.getName(), categoryDto.getName());
        }

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testProductListToDtoMapper() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(10L);
        productCategory.setName("Category");

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        List<Product> products = new ArrayList<>();

        Product product1 = new Product();
        product1.setId(10L);
        product1.setName("Product 1");
        product1.setPrice(100);
        product1.setAmount(25);
        product1.setDescription("some desc 1");
        product1.setCategories(productCategories);
        products.add(product1);

        Product product2 = new Product();
        product2.setId(20L);
        product2.setName("Product 2");
        product2.setPrice(8);
        product2.setAmount(15);
        product2.setDescription("some desc 2");
        product2.setCategories(productCategories);
        products.add(product2);

        List<ProductDto> productsDto = productMapper.toDtoList(products);
        Assertions.assertNotNull(productsDto);
        Assertions.assertEquals(products.size(), productsDto.size());

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            ProductDto productDto = productsDto.get(i);

            Assertions.assertEquals(product.getId(), productDto.getId());
            Assertions.assertEquals(product.getName(), productDto.getName());
            Assertions.assertEquals(product.getPrice(), productDto.getPrice());
            Assertions.assertEquals(product.getAmount(), productDto.getAmount());
            Assertions.assertEquals(product.getDescription(), productDto.getDescription());

            Assertions.assertNotNull(product.getCategories());
            Assertions.assertEquals(product.getCategories().size(), productDto.getCategories().size());

            for (int j = 0; j < product.getCategories().size(); j++) {
                ProductCategory category = product.getCategories().get(j);
                ProductCategoryDto categoryDto = productDto.getCategories().get(j);

                Assertions.assertEquals(category.getId(), categoryDto.getId());
                Assertions.assertEquals(category.getName(), categoryDto.getName());

            }

        }

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testCreateProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category");
        productCategoryRepository.save(productCategory);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        Product product = new Product();
        product.setName("Product");
        product.setPrice(100);
        product.setAmount(25);
        product.setDescription("some desc");
        product.setCategories(productCategories);

        productRepository.deleteAll();

        ProductDto createdProduct = productService.createProduct(productMapper.toDto(product));

        Assertions.assertNotNull(createdProduct);
        Assertions.assertNotNull(createdProduct.getId());

        List<Product> products = productRepository.findAll();
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);

        Product checkProduct = productRepository.findById(createdProduct.getId()).orElseThrow();
        Assertions.assertNotNull(checkProduct);
        Assertions.assertEquals(createdProduct.getId(), checkProduct.getId());
        Assertions.assertEquals(createdProduct.getName(), checkProduct.getName());
        Assertions.assertEquals(createdProduct.getPrice(), checkProduct.getPrice());
        Assertions.assertEquals(createdProduct.getAmount(), checkProduct.getAmount());
        Assertions.assertEquals(createdProduct.getDescription(), checkProduct.getDescription());

        Assertions.assertNotNull(checkProduct.getCategories());
        Assertions.assertEquals(checkProduct.getCategories().get(0), checkProduct.getCategories().get(0));

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testGetAllProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(productCategory);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(100);
        product1.setAmount(25);
        product1.setDescription("some desc 1");
        product1.setCategories(productCategories);


        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(8);
        product2.setAmount(15);
        product2.setDescription("some desc 2");
        product2.setCategories(productCategories);

        productRepository.deleteAll();

        ProductDto createdProduct1 = productService.createProduct(productMapper.toDto(product1));
        Assertions.assertNotNull(createdProduct1);
        Assertions.assertNotNull(createdProduct1.getId());

        ProductDto createdProduct2 = productService.createProduct(productMapper.toDto(product2));
        Assertions.assertNotNull(createdProduct2);
        Assertions.assertNotNull(createdProduct2.getId());

        List<ProductDto> productsDto = productService.getAllProduct();

        Assertions.assertNotNull(productsDto);
        Assertions.assertEquals(2, productsDto.size());

        ProductDto retrievedProduct1 = productsDto.get(0);
        Assertions.assertEquals(createdProduct1.getId(), retrievedProduct1.getId());
        Assertions.assertEquals(createdProduct1.getName(), retrievedProduct1.getName());
        Assertions.assertEquals(createdProduct1.getPrice(), retrievedProduct1.getPrice());
        Assertions.assertEquals(createdProduct1.getAmount(), retrievedProduct1.getAmount());
        Assertions.assertEquals(createdProduct1.getDescription(), retrievedProduct1.getDescription());
        Assertions.assertEquals(productCategory.getId(), retrievedProduct1.getCategories().get(0).getId());

        ProductDto retrievedProduct2 = productsDto.get(1);
        Assertions.assertEquals(createdProduct2.getId(), retrievedProduct2.getId());
        Assertions.assertEquals(createdProduct2.getName(), retrievedProduct2.getName());
        Assertions.assertEquals(createdProduct2.getPrice(), retrievedProduct2.getPrice());
        Assertions.assertEquals(createdProduct2.getAmount(), retrievedProduct2.getAmount());
        Assertions.assertEquals(createdProduct2.getDescription(), retrievedProduct2.getDescription());
        Assertions.assertEquals(productCategory.getId(), retrievedProduct2.getCategories().get(0).getId());


        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testUpdateProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(productCategory);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        Product product = new Product();
        product.setName("Product");
        product.setPrice(100);
        product.setAmount(25);
        product.setDescription("some desc");
        product.setCategories(productCategories);

        productRepository.deleteAll();

        productRepository.save(product);

        Product updatedProduct = new Product();
        updatedProduct.setId(product.getId());
        updatedProduct.setName("Updated Product");
        updatedProduct.setPrice(10);
        updatedProduct.setAmount(5);
        updatedProduct.setDescription("updated some desc");
        updatedProduct.setCategories(productCategories);

        ProductDto updatedProductDto = productMapper.toDto(updatedProduct);

        ProductDto updatedProductSec = productService.updateProduct(updatedProductDto);

        Assertions.assertNotNull(updatedProduct);
        Assertions.assertEquals(updatedProduct.getId(), updatedProductSec.getId());
        Assertions.assertEquals(updatedProduct.getName(), updatedProductSec.getName());
        Assertions.assertEquals(updatedProduct.getPrice(), updatedProductSec.getPrice());
        Assertions.assertEquals(updatedProduct.getAmount(), updatedProductSec.getAmount());
        Assertions.assertEquals(updatedProduct.getDescription(), updatedProductSec.getDescription());
        Assertions.assertEquals(updatedProduct.getCategories().get(0).getId(), updatedProductSec.getCategories().get(0).getId());

        Product updatedProductEntity = productRepository.findById(updatedProduct.getId()).orElse(null);
        Assertions.assertNotNull(updatedProductEntity);
        Assertions.assertEquals(updatedProductDto.getId(), updatedProductEntity.getId());
        Assertions.assertEquals(updatedProductDto.getName(), updatedProductEntity.getName());
        Assertions.assertEquals(updatedProductDto.getPrice(), updatedProductEntity.getPrice());
        Assertions.assertEquals(updatedProductDto.getAmount(), updatedProductEntity.getAmount());
        Assertions.assertEquals(updatedProductDto.getDescription(), updatedProductEntity.getDescription());
        Assertions.assertEquals(updatedProductDto.getCategories().get(0).getId(), updatedProductEntity.getCategories().get(0).getId());

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testGetProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(productCategory);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        Product product = new Product();
        product.setName("Product");
        product.setPrice(100);
        product.setAmount(25);
        product.setDescription("some desc");
        product.setCategories(productCategories);

        productRepository.deleteAll();

        productRepository.save(product);

        ProductDto getProduct = productService.getProduct(productMapper.toDto(product).getId());

        Assertions.assertNotNull(getProduct);
        Assertions.assertNotNull(getProduct.getId());

        List<Product> products = productRepository.findAll();
        Assertions.assertNotNull(products);
        Assertions.assertTrue(products.size() > 0);

        Product checkProduct = productRepository.findById(getProduct.getId()).orElseThrow();
        Assertions.assertNotNull(checkProduct);
        Assertions.assertEquals(getProduct.getId(), checkProduct.getId());
        Assertions.assertEquals(getProduct.getName(), checkProduct.getName());
        Assertions.assertEquals(getProduct.getPrice(), checkProduct.getPrice());
        Assertions.assertEquals(getProduct.getAmount(), checkProduct.getAmount());
        Assertions.assertEquals(getProduct.getDescription(), checkProduct.getDescription());

        Assertions.assertNotNull(checkProduct.getCategories().get(0));
        Assertions.assertEquals(checkProduct.getCategories().get(0).getId(), getProduct.getCategories().get(0).getId());

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testDeleteProduct() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(productCategory);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(productCategory);

        Product product = new Product();
        product.setName("Product");
        product.setPrice(100);
        product.setAmount(25);
        product.setDescription("some desc");
        product.setCategories(productCategories);

        productRepository.deleteAll();

        productRepository.save(product);

        productService.deleteProduct(product.getId());

        List<Product> products = productRepository.findAll();
        Assertions.assertTrue(products.isEmpty());

        Assertions.assertFalse(productRepository.findById(product.getId()).isPresent());

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    public void testGetProductByCategories() {
        ProductCategory category = new ProductCategory();
        category.setName("Category");

        productCategoryRepository.deleteAll();
        productCategoryRepository.save(category);

        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(category);

        Product product1 = new Product();
        product1.setName("Product 1");
        product1.setPrice(100);
        product1.setAmount(25);
        product1.setDescription("some desc 1");
        product1.setCategories(productCategories);

        Product product2 = new Product();
        product2.setName("Product 2");
        product2.setPrice(200);
        product2.setAmount(50);
        product2.setDescription("some desc 2");
        product2.setCategories(productCategories);

        Product product3 = new Product();
        product3.setName("Product 3");
        product3.setPrice(300);
        product3.setAmount(75);
        product3.setDescription("some desc 3");
        product3.setCategories(productCategories);

        productRepository.deleteAll();
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        Long categoryId = category.getId();
        List<ProductDto> productDtos = productService.getProductByCategories(categoryId);

        Assertions.assertNotNull(productDtos);
        Assertions.assertEquals(3, productDtos.size());

        ProductDto productDto1 = productMapper.toDto(productRepository.findById(product1.getId()).orElseThrow());
        Assertions.assertNotNull(productDto1);
        Assertions.assertEquals(product1.getName(), productDto1.getName());
        Assertions.assertEquals(product1.getPrice(), productDto1.getPrice());
        Assertions.assertEquals(product1.getAmount(), productDto1.getAmount());
        Assertions.assertEquals(product1.getDescription(), productDto1.getDescription());

        ProductDto productDto2 = productMapper.toDto(productRepository.findById(product2.getId()).orElseThrow());
        Assertions.assertNotNull(productDto2);
        Assertions.assertEquals(product2.getName(), productDto2.getName());
        Assertions.assertEquals(product2.getPrice(), productDto2.getPrice());
        Assertions.assertEquals(product2.getAmount(), productDto2.getAmount());
        Assertions.assertEquals(product2.getDescription(), productDto2.getDescription());

        ProductDto productDto3 = productMapper.toDto(productRepository.findById(product3.getId()).orElseThrow());
        Assertions.assertNotNull(productDto3);
        Assertions.assertEquals(product3.getName(), productDto3.getName());
        Assertions.assertEquals(product3.getPrice(), productDto3.getPrice());
        Assertions.assertEquals(product3.getAmount(), productDto3.getAmount());
        Assertions.assertEquals(product3.getDescription(), productDto3.getDescription());

        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }
}
