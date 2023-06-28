package kz.kairatuly.finalproject.api;

import kz.kairatuly.finalproject.dto.ProductDto;
import kz.kairatuly.finalproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productService.getAllProduct();
    }

    @GetMapping(value = "/{id}")
    public ProductDto getProduct(@PathVariable(name = "id") Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return productService.createProduct(product);
    }

    @PutMapping
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping(value = "/category/{categoryId}")
    public List<ProductDto> getProductsByCategory(@PathVariable(name = "categoryId") Long categoryId) {
        return productService.getProductByCategories(categoryId);
    }

    @GetMapping(value = "/catalog")
    public List<ProductDto> getAllProductsRandom() {
        return productService.getAllProductRandom();
    }
}
