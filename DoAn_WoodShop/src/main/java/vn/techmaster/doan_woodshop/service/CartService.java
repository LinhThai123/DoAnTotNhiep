package vn.techmaster.doan_woodshop.service;

import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.CartProduct;

import java.util.List;

@Service
public interface CartService {

   Long cartProducts (String cartId) ;

   Long countProducts (String cartId) ;

}
