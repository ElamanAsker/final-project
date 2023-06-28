package kz.kairatuly.finalproject.repositories;

import kz.kairatuly.finalproject.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post,Long> {
    Post findAllById(Long id);
}
