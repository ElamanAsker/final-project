package kz.kairatuly.finalproject;

import kz.kairatuly.finalproject.dto.ProductCategoryDto;
import kz.kairatuly.finalproject.entities.ProductCategory;
import kz.kairatuly.finalproject.mapper.ProductCategoryMapper;
import kz.kairatuly.finalproject.repositories.ProductCategoryRepository;
import kz.kairatuly.finalproject.services.ProductCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void testProductCategoryDtoMapper() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(10L);
        productCategory.setName("Category");
        productCategory.setDescription("Category Description");

        ProductCategoryDto productCategoryDto = productCategoryMapper.toDto(productCategory);

        Assertions.assertNotNull(productCategoryDto);
        Assertions.assertEquals(productCategory.getId(), productCategoryDto.getId());
        Assertions.assertEquals(productCategory.getName(), productCategoryDto.getName());
        Assertions.assertEquals(productCategory.getDescription(), productCategoryDto.getDescription());

    }

    @Test
    public void testProductCategoryListToDtoMapper() {
        List<ProductCategory> productCategories = new ArrayList<>();

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setId(10L);
        productCategory1.setName("Category 1");
        productCategory1.setDescription("Category 1 Description");
        productCategories.add(productCategory1);

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setId(20L);
        productCategory2.setName("Category 2");
        productCategory2.setDescription("Category 2 Description");
        productCategories.add(productCategory2);

        List<ProductCategoryDto> productCategoriesDto = productCategoryMapper.toDtoList(productCategories);
        Assertions.assertNotNull(productCategoriesDto);
        Assertions.assertEquals(productCategories.size(), productCategoriesDto.size());

        for (int i = 0; i < productCategories.size(); i++) {
            ProductCategory productCategory = productCategories.get(i);
            ProductCategoryDto productCategoryDtoDto = productCategoriesDto.get(i);

            Assertions.assertEquals(productCategory.getId(), productCategoryDtoDto.getId());
            Assertions.assertEquals(productCategory.getName(), productCategoryDtoDto.getName());
            Assertions.assertEquals(productCategory.getDescription(), productCategoryDtoDto.getDescription());
        }
    }

    @Test
    public void testCreateProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setName("Category");
        productCategory.setDescription("Category Description");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(productCategory);

        ProductCategoryDto createdProductCategory = productCategoryService.createCategory(productCategoryMapper.toDto(productCategory));
        Assertions.assertNotNull(createdProductCategory);
        Assertions.assertNotNull(createdProductCategory.getId());

        List<ProductCategory> productCategories = productCategoryRepository.findAll();
        Assertions.assertNotNull(productCategories);
        Assertions.assertTrue(productCategories.size() > 0);

        ProductCategory checkProductCategory = productCategoryRepository.findById(createdProductCategory.getId()).orElseThrow();
        Assertions.assertNotNull(checkProductCategory);
        Assertions.assertEquals(createdProductCategory.getId(), checkProductCategory.getId());
        Assertions.assertEquals(createdProductCategory.getName(), checkProductCategory.getName());
        Assertions.assertEquals(createdProductCategory.getDescription(), checkProductCategory.getDescription());

        productCategoryRepository.deleteAll();
    }

    @Test
    public void testGetAllProductCategory() {

        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setId(10L);
        productCategory1.setName("Category 1");
        productCategory1.setDescription("Category 1 Description");

        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setId(20L);
        productCategory2.setName("Category 2");
        productCategory2.setDescription("Category 2 Description");

        productCategoryRepository.deleteAll();

        ProductCategoryDto createdCategory1 = productCategoryService.createCategory(productCategoryMapper.toDto(productCategory1));
        Assertions.assertNotNull(createdCategory1);
        Assertions.assertNotNull(createdCategory1.getId());

        ProductCategoryDto createdCategory2 = productCategoryService.createCategory(productCategoryMapper.toDto(productCategory2));
        Assertions.assertNotNull(createdCategory2);
        Assertions.assertNotNull(createdCategory2.getId());

        List<ProductCategoryDto> categoriesDto = productCategoryService.getAllCategories();

        Assertions.assertNotNull(categoriesDto);
        Assertions.assertEquals(2, categoriesDto.size());

        ProductCategoryDto retrievedCategory1 = categoriesDto.get(0);
        Assertions.assertEquals(createdCategory1.getId(), retrievedCategory1.getId());
        Assertions.assertEquals(createdCategory1.getName(), retrievedCategory1.getName());
        Assertions.assertEquals(createdCategory1.getDescription(), retrievedCategory1.getDescription());

        ProductCategoryDto retrievedCategory2 = categoriesDto.get(1);
        Assertions.assertEquals(createdCategory2.getId(), retrievedCategory2.getId());
        Assertions.assertEquals(createdCategory2.getName(), retrievedCategory2.getName());
        Assertions.assertEquals(createdCategory2.getDescription(), retrievedCategory2.getDescription());

        productCategoryRepository.deleteAll();
    }

    @Test
    public void testUpdateProductCategory() {

        ProductCategory category = new ProductCategory();
        category.setName("Category");
        category.setDescription("Category Description");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(category);

        ProductCategory updatedCategory = new ProductCategory();
        updatedCategory.setId(category.getId());
        updatedCategory.setName("Updated Category");
        updatedCategory.setDescription("Updated Category Description");

        ProductCategoryDto updatedCategoryDto = productCategoryMapper.toDto(updatedCategory);

        ProductCategoryDto updatedCategorySec = productCategoryService.updateCategory(updatedCategoryDto);

        Assertions.assertNotNull(updatedCategory);
        Assertions.assertEquals(updatedCategory.getId(), updatedCategorySec.getId());
        Assertions.assertEquals(updatedCategory.getName(), updatedCategorySec.getName());
        Assertions.assertEquals(updatedCategory.getDescription(), updatedCategorySec.getDescription());

        ProductCategory updatedCategoryEntity = productCategoryRepository.findById(updatedCategory.getId()).orElse(null);
        Assertions.assertNotNull(updatedCategoryEntity);
        Assertions.assertEquals(updatedCategoryDto.getId(), updatedCategoryEntity.getId());
        Assertions.assertEquals(updatedCategoryDto.getName(), updatedCategoryEntity.getName());
        Assertions.assertEquals(updatedCategoryDto.getDescription(), updatedCategoryEntity.getDescription());

        productCategoryRepository.deleteAll();
    }

    @Test
    public void testGetProductCategory() {
        ProductCategory category = new ProductCategory();
        category.setName("Category");
        category.setDescription("Category Description");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(category);

        ProductCategoryDto getCategory = productCategoryService.getCategory(productCategoryMapper.toDto(category).getId());
        Assertions.assertNotNull(getCategory);
        Assertions.assertNotNull(getCategory.getId());

        List<ProductCategory> categories = productCategoryRepository.findAll();
        Assertions.assertNotNull(categories);
        Assertions.assertTrue(categories.size() > 0);

        ProductCategory checkCategory = productCategoryRepository.findById(getCategory.getId()).orElseThrow();
        Assertions.assertNotNull(checkCategory);
        Assertions.assertEquals(getCategory.getId(), checkCategory.getId());
        Assertions.assertEquals(getCategory.getName(), checkCategory.getName());
        Assertions.assertEquals(getCategory.getDescription(), checkCategory.getDescription());

        productCategoryRepository.deleteAll();
    }

    @Test
    public void testDeleteProductCategory() {
        ProductCategory category = new ProductCategory();
        category.setName("Category");
        category.setDescription("Category Description");

        productCategoryRepository.deleteAll();

        productCategoryRepository.save(category);

        productCategoryService.deleteCategory(category.getId());

        List<ProductCategory> categories = productCategoryRepository.findAll();
        Assertions.assertTrue(categories.isEmpty());

        Assertions.assertFalse(productCategoryRepository.findById(category.getId()).isPresent());

        productCategoryRepository.deleteAll();

    }
}
