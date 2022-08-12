package vn.techmaster.doan_woodshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.doan_woodshop.entity.CartProduct;
import vn.techmaster.doan_woodshop.entity.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    long countByCartId(String id);

    List<CartProduct> findByCartId(String cartId);

    List<CartProduct> findByProductId(String productId) ;

    Optional<CartProduct> findByCartIdAndProductId(String cartId, String productId);

}
