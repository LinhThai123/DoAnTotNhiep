package vn.techmaster.doan_woodshop.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.Category;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.model.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {

    List<Product> findAll() ;

    <S extends Product> S save(S entity);

    Optional<Product> findById(String id);

    void delete(Product entity);

    Page<Product> findByNameContaining(String keyword, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    public List<Product> getListNewProduct();

    public List<Product> getListSuggestProduct();

}
