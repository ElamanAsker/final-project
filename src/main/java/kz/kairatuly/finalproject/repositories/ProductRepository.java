package kz.kairatuly.finalproject.repositories;

import kz.kairatuly.finalproject.entities.Pictures;
import kz.kairatuly.finalproject.entities.Product;
import kz.kairatuly.finalproject.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategories(ProductCategory category);
}
