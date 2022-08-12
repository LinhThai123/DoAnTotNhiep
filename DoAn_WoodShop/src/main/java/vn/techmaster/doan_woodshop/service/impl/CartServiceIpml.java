package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.repository.CartProductRepository;
import vn.techmaster.doan_woodshop.service.CartService;

@Component
public class CartServiceIpml implements CartService {
    @Autowired
    private CartProductRepository cartProductRepository ;
    @Override
    public Long cartProducts(String cartId) {
       Long sum =  cartProductRepository.findByCartId(cartId).stream().map(p-> p.getQuantity()*p.getProduct().getPrice()).reduce(0L, Long::sum);
       return sum ;
    }

    @Override
    public Long countProducts(String cartId) {
        Long quantity = cartProductRepository.findAll().stream().filter(p -> p.getCart().getId().equals(cartId)).map(p -> p.getQuantity()).reduce(0L, Long::sum);
        return quantity;
    }
}
