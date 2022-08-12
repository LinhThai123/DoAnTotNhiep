package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.Category;
import vn.techmaster.doan_woodshop.repository.CategoryRepository;
import vn.techmaster.doan_woodshop.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Page<Category> findByNameContaining(String keyword, Pageable pageable) {
        return categoryRepository.findByNameContaining(keyword, pageable);
    }
    @Override
    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }


    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public boolean existsByCode(String code) {
        return categoryRepository.existsByCode(code);
    }

    @Override
    public boolean existsByNameOrCode(String name, String code) {
        return categoryRepository.existsByNameOrCode(name, code);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
