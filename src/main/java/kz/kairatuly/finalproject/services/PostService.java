package kz.kairatuly.finalproject.services;

import kz.kairatuly.finalproject.dto.PostDto;
import kz.kairatuly.finalproject.entities.Post;
import kz.kairatuly.finalproject.mapper.PostMapper;
import kz.kairatuly.finalproject.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private MyUserService myUserService;

    public PostDto createPost(PostDto post) {
        Post postEntity = postMapper.toEntity(post);
        if (!(postEntity.getAuthor() != null && postEntity.getAuthor().getId() != null)) {
            postEntity.setAuthor(myUserService.getCurrentUser());
        }
        return postMapper.toDto(postRepository.save(postEntity));
    }

    public List<PostDto> getAllPost() {
        return postMapper.toDtoList(postRepository.findAll());
    }

    public PostDto getPost(Long id) {
        return postMapper.toDto(postRepository.findById(id).orElseThrow());
    }

    public PostDto updatePost(PostDto post) {
        Post postEntity = postMapper.toEntity(post);
        if (!(postEntity.getAuthor() != null && postEntity.getAuthor().getId() != null)) {
            postEntity.setAuthor(myUserService.getCurrentUser());
        }
        return postMapper.toDto(postRepository.save(postEntity));
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
