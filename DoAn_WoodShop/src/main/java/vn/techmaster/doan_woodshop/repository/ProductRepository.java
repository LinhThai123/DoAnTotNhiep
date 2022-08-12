package vn.techmaster.doan_woodshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.model.dto.ProductDTO;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM product pro \n" +
            "WHERE pro.status = 'DANGBAN' \n" +
            "ORDER BY create_at desc ")
    public List<Product> getListNewProduct();

    @Query(nativeQuery = true, value = "SELECT * \n" +
            "FROM product pro \n" +
            "WHERE pro.status = 'DANGBAN' AND pro.id IN (?1) \n" +
            "LIMIT ?2 \n")
    public List<Product> getListSuggestProduct(String id , int limit);

    @Query(nativeQuery = true , value = "SELECT * \n" +
            "FROM category c , product p \n" +
            "WHERE c.id = p.category_id \n AND c.id IN (?1) \n")
    public List<Product> getListCategory(@Param("id") String id) ;

    Page<Product> findByNameContaining(String keyword, Pageable pageable);



}
