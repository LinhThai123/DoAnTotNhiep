package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.Status;
import vn.techmaster.doan_woodshop.exception.NotFoundException;
import vn.techmaster.doan_woodshop.repository.ProductRepository;
import vn.techmaster.doan_woodshop.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductServiceIpml implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public <S extends Product> S save(S entity) {
        return productRepository.save(entity);
    }

    @Override
    public Optional<Product> findById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product;
        }
        throw new NotFoundException("Could not find any product with id " + id);
    }

    @Override
    public void delete(Product entity) {
        productRepository.delete(entity);
    }

    @Override
    public Page<Product> findByNameContaining(String keyword, Pageable pageable) {
        return productRepository.findByNameContaining(keyword, pageable);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getListNewProduct() {

        List<Product> newProducts = productRepository.getListNewProduct();
        List<Product> newList = new ArrayList<>();
        if (newProducts.size() >= 8) {
            for (int i = 0; i < 8; i++) {
                newList.add(newProducts.get(i));
            }
        } else {
            for (int i = 0; i < newProducts.size(); i++) {
                newList.add(newProducts.get(i));
            }
        }
        return newList;
    }
    @Override
    public List<Product> getListSuggestProduct() {
        return productRepository.findAll().stream().filter(p->p.getStatus().
                equals(Status.DANGBAN)).sorted((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt())).limit(3)
                .collect(Collectors.toList());
    }

}
