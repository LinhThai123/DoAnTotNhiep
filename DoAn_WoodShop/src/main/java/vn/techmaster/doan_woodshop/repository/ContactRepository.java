package vn.techmaster.doan_woodshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.doan_woodshop.entity.Feeback;
import vn.techmaster.doan_woodshop.entity.Post;

@Repository
public interface ContactRepository extends JpaRepository<Feeback , String> {
    Page<Feeback> findAllByTitleContains (String keyword, Pageable pageable);
}
