package vn.techmaster.doan_woodshop.controller.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.BlogService;
import vn.techmaster.doan_woodshop.service.CartService;

import java.util.List;

@Controller
@RequestMapping("/new")
public class NewController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String getNewPage(Model model) {
        List<Post> allPost = blogService.getAllNews();
        model.addAttribute("allPost", allPost);

        List<Post> newPost = blogService.getListNewPost();
        model.addAttribute("newPost" , newPost) ;

        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("user" , user);

        model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        return "client/allnews";
    }
}
