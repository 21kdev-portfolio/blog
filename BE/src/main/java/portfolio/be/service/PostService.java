package portfolio.be.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.be.entity.Post;
import portfolio.be.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Long> getAllIds() {
        return postRepository.findAllIds();
    }

    public Post findById(Long id) {
        Optional<Post> blog = postRepository.findById(id);
        return blog.orElse(null);
    }
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}