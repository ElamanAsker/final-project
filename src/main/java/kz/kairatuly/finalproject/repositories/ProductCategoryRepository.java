package kz.kairatuly.finalproject.repositories;

import kz.kairatuly.finalproject.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
    ProductCategory findAllById(Long id);
}
