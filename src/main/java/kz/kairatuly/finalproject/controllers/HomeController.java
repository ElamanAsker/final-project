package kz.kairatuly.finalproject.controllers;

import kz.kairatuly.finalproject.dto.PicturesDto;
import kz.kairatuly.finalproject.dto.ProductDto;
import kz.kairatuly.finalproject.services.FileService;
import kz.kairatuly.finalproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private FileService fileService;

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/")
    public String openIndex(Model model) {
        return "index";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/admin")
    public String openAdminPage(Model model) {
        return "admin";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/details/{id}")
    public String openDetails(@PathVariable(name = "id") Long id, Model model) {
        Long postId = id;
        model.addAttribute("postId", postId);
        return "details";
    }

    @GetMapping(value = "/post/{id}")
    public String openPostPage(@PathVariable(name = "id") Long id, Model model) {
        Long postId = id;
        model.addAttribute("postId", postId);
        return "post";
    }

    @PostMapping(value = "/post-pic")
    public String uploadPostPicture(@RequestParam(name = "post_picture") MultipartFile file,
                                    @RequestParam(name = "postId") Long id) {
        fileService.uploadPostPicture(file, id);
        return "redirect:news";
    }

    @PostMapping(value = "/category-pic")
    public String uploadCategoryPicture(@RequestParam(name = "category_picture") MultipartFile file,
                                        @RequestParam(name = "categoryId") Long id) {
        fileService.uploadCategoryPicture(file, id);
        return "redirect:category";
    }

    @GetMapping(value = "/news")
    public String openNewsPage(Model model) {
        return "news";
    }


    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/category")
    public String openAddCategory(Model model) {
        return "category";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/details-category/{id}")
    public String openDetailsCategory(@PathVariable(name = "id") Long id, Model model) {
        Long categoryId = id;
        model.addAttribute("categoryId", categoryId);
        return "details-category";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/product")
    public String openProductPage(Model model) {
        return "product";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/add-product/{id}")
    public String openAddProductPage(@PathVariable(name = "id") Long id, Model model) {
        Long categoryId = id;
        model.addAttribute("categoryId", categoryId);
        return "add-product";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/details-product/{id}")
    public String openDetailsProduct(@PathVariable(name = "id") Long id, Model model) {
        Long productId = id;

        model.addAttribute("productId", productId);

        ProductDto productDto = productService.getProduct(id);
        List<PicturesDto> productPictures = productDto.getPictures();
        model.addAttribute("pictures",productPictures);
        return "details-product";
    }

    @PostMapping(value = "/product-pic")
    public String uploadProductPicture(@RequestParam(name = "product_picture") MultipartFile file,
                                        @RequestParam(name = "productId") Long id) {
        fileService.uploadProductPicture(file, id);
        return "redirect:/details-product/" + id;
    }

    @GetMapping(value = "/shop")
    public String openShopPage(){
        return "shop";
    }

    @GetMapping(value = "/product-info/{id}")
    public String openProductInfo(@PathVariable(name = "id") Long id,Model model){
        model.addAttribute("productId",id);
        return "product-info";
    }

    @GetMapping(value = "/product-categories/{id}")
    public String openProductByCategory(@PathVariable(name = "id") Long id, Model model) {
        Long categoryId = id;
        model.addAttribute("categoryId", categoryId);
        return "product-categories";
    }
}
