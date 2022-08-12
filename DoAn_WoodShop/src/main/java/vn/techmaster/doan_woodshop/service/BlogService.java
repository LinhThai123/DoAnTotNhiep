package vn.techmaster.doan_woodshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.dto.PostDTO;
import vn.techmaster.doan_woodshop.model.request.PostReq;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public interface BlogService {

    <S extends Post> S save(S entity);

    Optional<Post> findById(String id);

    void delete(Post entity);

    List<Post> getAllNews () ;

    List<Post> getListNewPost () ;

    Page<Post> findAllByTitleContains(String keyword, Pageable pageable);
}
