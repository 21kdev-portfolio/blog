package portfolio.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.be.entity.Post;
import portfolio.be.service.PostService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostService postService;

    @Autowired
    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post savedPost = postService.savePost(post);
        return new ResponseEntity<>(savedPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> editPost(@PathVariable Long id, @RequestBody Post post) {
        Optional<Post> existingPost = Optional.ofNullable(postService.findById(id));
        if (existingPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        post.setId(id);
        Post updatedPost = postService.savePost(post);
        return ResponseEntity.ok(updatedPost);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        Optional<Post> existingPost = Optional.ofNullable(postService.findById(id));
        if (existingPost.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build(); // 삭제 후 본문 없는 응답 반환
    }


    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = Optional.ofNullable(postService.findById(id));
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getAllIds() {
        List<Long> ids = postService.getAllIds();
        return ResponseEntity.ok(ids);
    }
}