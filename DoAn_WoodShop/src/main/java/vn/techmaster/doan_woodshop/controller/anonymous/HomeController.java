package vn.techmaster.doan_woodshop.controller.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.dto.CartProductDTO;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.BlogService;
import vn.techmaster.doan_woodshop.service.CartService;
import vn.techmaster.doan_woodshop.service.ProductService;
import vn.techmaster.doan_woodshop.service.impl.ProductServiceIpml;
import vn.techmaster.doan_woodshop.utils.AddToCart;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired private ProductService productService ;

    @Autowired private BlogService blogService ;

    @Autowired private ProductServiceIpml productServiceIpml ;

    @Autowired private CartService cartService ;

    @GetMapping("")
    public String getHomePage (Model model) {
        // get All list product
        List<Product> allProducts = productService.findAll();
        model.addAttribute("allProducts" , allProducts) ;

        // get new list product
        List<Product> newProducts = productService.getListNewProduct();
        model.addAttribute("newProducts", newProducts);

        // get list news
        List<Post> allPost = blogService.getListNewPost();
        model.addAttribute("allPost" , allPost);
        return "client/index" ;
    }
    @GetMapping("/detail/{id}")
    public String DetailProduct (Model model , @PathVariable String id) {
        model.addAttribute("products" , productServiceIpml.findById(id).get()) ;
        return "client/detail" ;
    }
}
