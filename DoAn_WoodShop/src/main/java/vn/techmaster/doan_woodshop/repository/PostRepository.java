package vn.techmaster.doan_woodshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.model.dto.PostDTO;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, String> {

    public Page<Post> findAllByStatus(int status, Pageable pageable);

    Page<Post> findAllByTitleContains (String keyword, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM post p \n " +
            "WHERE p.status = 'Public' \n" +
            "ORDER BY create_at desc")
    public List<Post> getListNewPost();
}
