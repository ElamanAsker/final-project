package kz.kairatuly.finalproject.services;

import kz.kairatuly.finalproject.entities.*;
import kz.kairatuly.finalproject.repositories.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Value("${loadUrl}")
    private String loadUrl;

    @Value("${uploadURL}")
    private String uploadURL;

    @Autowired
    private MyUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private PicturesRepository picturesRepository;

    @Autowired
    private ProductRepository productRepository;

    public boolean uploadAvatar(MultipartFile file) {
        if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            try {
                User currentUser = userService.getCurrentUser();

                byte bytes[] = file.getBytes();
                String fileName = DigestUtils.sha1Hex(currentUser.getId() + "");
                String filePath = uploadURL + fileName + ".jpg";
                Path path = Paths.get(filePath);
                Files.write(path, bytes);

                currentUser.setPicture(fileName);
                userRepository.save(currentUser);

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public byte[] getAvatar(String url) throws IOException {
        String image = null;
        User currentUser = userService.getCurrentUser();
        if (currentUser != null && currentUser.getPicture() != null && currentUser.getPicture().equals(url)) {
            image = loadUrl + url + ".jpg";
        }

        InputStream in;

        try {

            ClassPathResource resource = new ClassPathResource(image);
            in = resource.getInputStream();

        } catch (Exception e) {
            ClassPathResource resource = new ClassPathResource(loadUrl + "default.png");
            in = resource.getInputStream();
        }

        return IOUtils.toByteArray(in);
    }

    public boolean uploadPostPicture(MultipartFile file, Long id) {
        if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            try {
                byte bytes[] = file.getBytes();
                String fileName = "photo" + id;
                String filePath = "build/resources/main/static/pictures/" + fileName + ".jpg";
                Path path = Paths.get(filePath);
                Files.write(path, bytes);
                Post post = postRepository.findAllById(id);
                post.setPicture(fileName);
                postRepository.save(post);
                return true;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean uploadCategoryPicture(MultipartFile file, Long id) {
        if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            try {
                byte bytes[] = file.getBytes();
                String fileName = "photo" + id;
                String filePath = "build/resources/main/static/category/" + fileName + ".jpg";
                Path path = Paths.get(filePath);
                Files.write(path, bytes);
                ProductCategory productCategory = productCategoryRepository.findAllById(id);
                productCategory.setPicture(fileName);
                productCategoryRepository.save(productCategory);
                return true;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public boolean uploadProductPicture(MultipartFile file, Long id) {
        if (file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")) {
            try {
                byte bytes[] = file.getBytes();
                String fileName = "picture" + id + "-" + UUID.randomUUID().toString();
                String filePath = "build/resources/main/static/product/" + fileName + ".jpg";
                Path path = Paths.get(filePath);
                Files.write(path, bytes);

                Pictures picture = new Pictures();
                picture.setImage(fileName);
                Product product = productRepository.getById(id);
                List<Pictures> pictures = product.getPictures();
                pictures.add(picture);
                product.setPictures(pictures);
                picturesRepository.save(picture);
                productRepository.save(product);
                return true;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
