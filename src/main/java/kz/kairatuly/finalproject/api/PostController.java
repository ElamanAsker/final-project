package kz.kairatuly.finalproject.api;

import kz.kairatuly.finalproject.dto.PostDto;
import kz.kairatuly.finalproject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<PostDto> getPosts() {
        return postService.getAllPost();
    }

    @GetMapping(value = "/{id}")
    public PostDto getPosts(@PathVariable(name = "id") Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public PostDto addPost(@RequestBody PostDto post) {
        return postService.createPost(post);
    }

    @PutMapping
    public PostDto updatePost(@RequestBody PostDto post) {
        return postService.updatePost(post);
    }

    @DeleteMapping(value = "/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }
}
