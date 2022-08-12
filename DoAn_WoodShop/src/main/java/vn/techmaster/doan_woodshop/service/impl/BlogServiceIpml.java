package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.exception.NotFoundException;
import vn.techmaster.doan_woodshop.model.dto.PostDTO;
import vn.techmaster.doan_woodshop.repository.PostRepository;
import vn.techmaster.doan_woodshop.service.BlogService;

import java.util.List;
import java.util.Optional;

@Component
public class BlogServiceIpml implements BlogService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public <S extends Post> S save(S entity) {
        return postRepository.save(entity);
    }

    @Override
    public Optional<Post> findById(String id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post;
        }
        throw new NotFoundException("Could not find any post with id " + id);
    }

    @Override
    public void delete(Post entity) {
        postRepository.delete(entity);
    }

    @Override
    public List<Post> getAllNews() {
        return postRepository.findAll();
    }
    @Override
    public List<Post> getListNewPost() {
        return postRepository.getListNewPost();
    }

    @Override
    public Page<Post> findAllByTitleContains(String keyword, Pageable pageable) {
        return postRepository.findAllByTitleContains(keyword, pageable);
    }
}
