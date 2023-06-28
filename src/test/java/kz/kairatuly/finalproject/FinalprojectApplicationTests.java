package kz.kairatuly.finalproject;

import kz.kairatuly.finalproject.dto.PostDto;
import kz.kairatuly.finalproject.entities.Post;
import kz.kairatuly.finalproject.entities.User;
import kz.kairatuly.finalproject.mapper.PostMapper;
import kz.kairatuly.finalproject.repositories.PostRepository;
import kz.kairatuly.finalproject.services.MyUserService;
import kz.kairatuly.finalproject.services.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FinalprojectApplicationTests {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private MyUserService userService;

    @Autowired
    private PostRepository postRepository;


    @Test
    public void testPostDtoMapper() {

        User user = new User();
        user.setId(10L);
        user.setFullName("Kairatuly Elaman");
        user.setEmail("asskerovv@mail.ru");

        Post post = new Post();
        post.setId(10L);
        post.setAuthor(user);
        post.setTitle("New Post");
        post.setContent("New Post Content");

        PostDto postDto = postMapper.toDto(post);

        Assertions.assertNotNull(postDto);
        Assertions.assertEquals(post.getId(), postDto.getId());
        Assertions.assertEquals(post.getTitle(), postDto.getTitle());
        Assertions.assertEquals(post.getContent(), postDto.getContent());
        Assertions.assertNotNull(postDto.getAuthor());
        Assertions.assertEquals(post.getAuthor().getId(), postDto.getAuthor().getId());
        Assertions.assertEquals(post.getAuthor().getEmail(), postDto.getAuthor().getEmail());
        Assertions.assertEquals(post.getAuthor().getFullName(), postDto.getAuthor().getFullName());

        postRepository.deleteAll();
        userService.deleteUser(user.getId());
    }

    @Test
    public void testPostListDtoMapper() {
        User user = new User();
        user.setId(10L);
        user.setFullName("Kairatuly Elaman");
        user.setEmail("asskerovv@mail.ru");

        List<Post> posts = new ArrayList<>();

        Post post1 = new Post();
        post1.setId(10L);
        post1.setAuthor(user);
        post1.setTitle("Post map 1");
        post1.setContent("Post map 1 Content");
        posts.add(post1);

        Post post2 = new Post();
        post2.setId(20L);
        post2.setAuthor(user);
        post2.setTitle("Post map 2");
        post2.setContent("Post map 2 Content");
        posts.add(post2);

        List<PostDto> postsDto = postMapper.toDtoList(posts);
        Assertions.assertNotNull(postsDto);
        Assertions.assertEquals(posts.size(), postsDto.size());

        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            PostDto postDto = postsDto.get(i);

            Assertions.assertEquals(post.getId(), postDto.getId());
            Assertions.assertEquals(post.getTitle(), postDto.getTitle());
            Assertions.assertEquals(post.getContent(), postDto.getContent());
            Assertions.assertNotNull(postDto.getAuthor());
            Assertions.assertEquals(post.getAuthor().getId(), postDto.getAuthor().getId());
            Assertions.assertEquals(post.getAuthor().getEmail(), postDto.getAuthor().getEmail());
            Assertions.assertEquals(post.getAuthor().getFullName(), postDto.getAuthor().getFullName());
        }

        postRepository.deleteAll();
        userService.deleteUser(user.getId());
    }

    @Test
    public void checkPostInsert() {
        User user = createUser();
        user = userService.insertUser(user);

        Post post = new Post();
        post.setAuthor(user);
        post.setContent("My Content Text");
        post.setTitle("My Title");

        postRepository.deleteAll();

        PostDto createdPost = postService.createPost(postMapper.toDto(post));

        Assertions.assertNotNull(createdPost);
        Assertions.assertNotNull(createdPost.getId());

        List<Post> posts = postRepository.findAll();
        Assertions.assertNotNull(posts);
        Assertions.assertTrue(posts.size() > 0);

        Post checkPost = postRepository.findById(createdPost.getId()).orElseThrow();
        Assertions.assertNotNull(checkPost);
        Assertions.assertEquals(createdPost.getId(), checkPost.getId());
        Assertions.assertEquals(createdPost.getTitle(), checkPost.getTitle());
        Assertions.assertEquals(createdPost.getContent(), checkPost.getContent());

        Assertions.assertNotNull(checkPost.getAuthor());
        Assertions.assertEquals(checkPost.getAuthor().getId(), createdPost.getAuthor().getId());

        postRepository.deleteAll();
        userService.deleteUser(user.getId());

    }

    @Test
    public void testUpdatePost() {
        User user = createUser();
        user = userService.insertUser(user);

        Post post = new Post();
        post.setAuthor(user);
        post.setContent("Initial Content");
        post.setTitle("Initial Title");

        postRepository.deleteAll();

        postRepository.save(post);

        Post updatedPost = new Post();
        updatedPost.setId(post.getId());
        updatedPost.setAuthor(user);
        updatedPost.setContent("Updated Content");
        updatedPost.setTitle("Updated Title");

        PostDto updatedPostDto = postMapper.toDto(updatedPost);

        PostDto updatedPostSec = postService.updatePost(updatedPostDto);

        Assertions.assertNotNull(updatedPost);
        Assertions.assertEquals(updatedPostDto.getId(), updatedPostSec.getId());
        Assertions.assertEquals(updatedPostDto.getAuthor().getId(), updatedPostSec.getAuthor().getId());
        Assertions.assertEquals(updatedPostDto.getContent(), updatedPostSec.getContent());
        Assertions.assertEquals(updatedPostDto.getTitle(), updatedPostSec.getTitle());

        Post updatedPostEntity = postRepository.findById(updatedPost.getId()).orElse(null);
        Assertions.assertNotNull(updatedPostEntity);
        Assertions.assertEquals(updatedPostDto.getId(), updatedPostEntity.getId());
        Assertions.assertEquals(updatedPostDto.getAuthor().getId(), updatedPostEntity.getAuthor().getId());
        Assertions.assertEquals(updatedPostDto.getContent(), updatedPostEntity.getContent());
        Assertions.assertEquals(updatedPostDto.getTitle(), updatedPostEntity.getTitle());

        postRepository.deleteAll();
        userService.deleteUser(user.getId());
    }

    @Test
    public void testGetPost() {
        User user = createUser();
        user = userService.insertUser(user);

        Post post = new Post();
        post.setAuthor(user);
        post.setContent("Initial Content");
        post.setTitle("Initial Title");

        postRepository.deleteAll();

        postRepository.save(post);

        PostDto getPost = postService.getPost(postMapper.toDto(post).getId());

        Assertions.assertNotNull(getPost);
        Assertions.assertNotNull(getPost.getId());

        List<Post> posts = postRepository.findAll();
        Assertions.assertNotNull(posts);
        Assertions.assertTrue(posts.size() > 0);

        Post checkPost = postRepository.findById(getPost.getId()).orElseThrow();
        Assertions.assertNotNull(checkPost);
        Assertions.assertEquals(getPost.getId(), checkPost.getId());
        Assertions.assertEquals(getPost.getTitle(), checkPost.getTitle());
        Assertions.assertEquals(getPost.getContent(), checkPost.getContent());

        Assertions.assertNotNull(checkPost.getAuthor());
        Assertions.assertEquals(checkPost.getAuthor().getId(), getPost.getAuthor().getId());

        postRepository.deleteAll();
        userService.deleteUser(user.getId());

    }

    @Test
    public void testDeletePost() {
        User user = createUser();
        user = userService.insertUser(user);

        Post post = new Post();
        post.setAuthor(user);
        post.setContent("Initial Content");
        post.setTitle("Initial Title");

        postRepository.deleteAll();

        postRepository.save(post);

        postService.deletePost(post.getId());

        List<Post> posts = postRepository.findAll();
        Assertions.assertTrue(posts.isEmpty());

        Assertions.assertFalse(postRepository.findById(post.getId()).isPresent());

        userService.deleteUser(user.getId());


    }

    @Test
    public void testGetAllPosts() {
        User user = createUser();
        user = userService.insertUser(user);

        Post post1 = new Post();
        post1.setAuthor(user);
        post1.setContent("My Content Text 1");
        post1.setTitle("My Title 1");

        Post post2 = new Post();
        post2.setAuthor(user);
        post2.setContent("My Content Text 2");
        post2.setTitle("My Title 2");

        postRepository.deleteAll();

        PostDto createdPost1 = postService.createPost(postMapper.toDto(post1));
        Assertions.assertNotNull(createdPost1);
        Assertions.assertNotNull(createdPost1.getId());

        PostDto createdPost2 = postService.createPost(postMapper.toDto(post2));
        Assertions.assertNotNull(createdPost2);
        Assertions.assertNotNull(createdPost2.getId());

        List<PostDto> postsDto = postService.getAllPost();

        Assertions.assertNotNull(postsDto);
        Assertions.assertEquals(2, postsDto.size());

        PostDto retrievedPost1 = postsDto.get(0);
        Assertions.assertEquals(createdPost1.getId(), retrievedPost1.getId());
        Assertions.assertEquals(post1.getTitle(), retrievedPost1.getTitle());
        Assertions.assertEquals(post1.getContent(), retrievedPost1.getContent());
        Assertions.assertEquals(user.getId(), retrievedPost1.getAuthor().getId());

        PostDto retrievedPost2 = postsDto.get(1);
        Assertions.assertEquals(createdPost2.getId(), retrievedPost2.getId());
        Assertions.assertEquals(post2.getTitle(), retrievedPost2.getTitle());
        Assertions.assertEquals(post2.getContent(), retrievedPost2.getContent());
        Assertions.assertEquals(user.getId(), retrievedPost2.getAuthor().getId());

        postRepository.deleteAll();
        userService.deleteUser(user.getId());
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setFullName("Test Testov");
        return user;
    }
}
