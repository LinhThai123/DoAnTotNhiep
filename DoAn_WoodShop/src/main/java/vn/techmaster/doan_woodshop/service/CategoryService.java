package vn.techmaster.doan_woodshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.Category;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    public Optional<Category> findById(String id);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    <S extends Category> S save(S entity);

    public Category addCategory(Category category) ;

    boolean existsByName(String name);

    boolean existsByCode(String name);

    boolean existsByNameOrCode(String name, String code);

    List<Category> findAll() ;
}
