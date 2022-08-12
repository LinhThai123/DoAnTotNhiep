package vn.techmaster.doan_woodshop.controller.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import vn.techmaster.doan_woodshop.entity.CartProduct;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.repository.CartProductRepository;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.CartService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    CartProductRepository cartProductRepository;

    @Autowired
    CartService cartService ;

    @GetMapping("/cart")
    public String getCartPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            model.addAttribute("carts", cartProductRepository.findByCartId(user.getCart().getId()));
            model.addAttribute("total", cartService.cartProducts(user.getCart().getId())) ;
            model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        }
        return "client/cart";
    }
    @GetMapping("/cart/{id}")
    public ModelAndView deleteCartItem(@PathVariable(value = "id") Long id , ModelMap model) {
        Optional<CartProduct> cartProduct = Optional.of(cartProductRepository.findById(id).get());
        if(cartProduct.isPresent()){
            cartProductRepository.delete(cartProduct.get());
        }
        return new ModelAndView("redirect:/cart" , model) ;
    }
}
