package kz.kairatuly.finalproject.api;

import kz.kairatuly.finalproject.dto.ProductCategoryDto;
import kz.kairatuly.finalproject.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/category")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategoryDto> getALlCategories(){
        return productCategoryService.getAllCategories();
    }

    @GetMapping(value = "/{id}")
    public ProductCategoryDto getCategory(@PathVariable(name = "id") Long id){
        return productCategoryService.getCategory(id);
    }

    @PostMapping
    public ProductCategoryDto createCategory(@RequestBody ProductCategoryDto category){
        return productCategoryService.createCategory(category);
    }

    @PutMapping
    public ProductCategoryDto updateCategory(@RequestBody ProductCategoryDto category){
        return productCategoryService.updateCategory(category);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategory(@PathVariable(name = "id") Long id){
        productCategoryService.deleteCategory(id);
    }
}
