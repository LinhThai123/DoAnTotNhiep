package vn.techmaster.doan_woodshop.controller.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.techmaster.doan_woodshop.entity.CartProduct;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.dto.CartProductDTO;
import vn.techmaster.doan_woodshop.repository.CartProductRepository;
import vn.techmaster.doan_woodshop.repository.CartRepository;
import vn.techmaster.doan_woodshop.repository.ProductRepository;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;

import java.util.Optional;

@RestController
public class ApiCartController {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/api/cart", method = RequestMethod.POST)
    public long addToCart(@RequestBody CartProductDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long sumQuantity = 0;
        if (authentication != null) {
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            CartProduct cartProduct = new CartProduct();
            Product product = productRepository.findById(dto.getProductId()).get();
            cartProduct.setCart(user.getCart());
            cartProduct.setProduct(product);
            cartProduct.setQuantity(1L);

            Optional<CartProduct> optionalCartProduct = cartProductRepository.findByCartIdAndProductId(cartProduct.getCart().getId(), cartProduct.getProduct().getId());

            if (optionalCartProduct.isPresent()) {
                optionalCartProduct.get().setQuantity(optionalCartProduct.get().getQuantity() + 1);
                cartProductRepository.save(optionalCartProduct.get());
            } else {
                cartProductRepository.save(cartProduct);
            }
            for (CartProduct cp : cartProductRepository.findByCartId(cartProduct.getCart().getId())) {
                sumQuantity += cp.getQuantity();
            }
            return sumQuantity;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/api/updateQuantity", method = RequestMethod.PUT)
    public long updateQuantity(@RequestBody CartProductDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long sumQuantity = 0;
        if (authentication != null) {
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            CartProduct cartProduct = new CartProduct();
            Product product = productRepository.findById(dto.getProductId()).get();
            cartProduct.setCart(user.getCart());
            cartProduct.setProduct(product);

            Optional<CartProduct> optionalCartProduct = cartProductRepository.findByCartIdAndProductId(cartProduct.getCart().getId(), cartProduct.getProduct().getId());

            if (optionalCartProduct.isPresent()) {
                optionalCartProduct.get().setQuantity(dto.getQuantity());
                cartProductRepository.save(optionalCartProduct.get());
            } else {
                cartProductRepository.save(cartProduct);
            }
            for (CartProduct cp : cartProductRepository.findByCartId(cartProduct.getCart().getId())) {
                sumQuantity += cp.getQuantity();
            }
            return sumQuantity;
        } else {
            return 0;
        }
    }
}
