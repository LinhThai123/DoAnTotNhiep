package vn.techmaster.doan_woodshop.controller.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.CartService;

@Controller
public class AboutController {
    @Autowired
    CartService cartService ;

    @GetMapping("/about")
    public String getIntroductPage (Model model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        return "client/about" ;
    }
}
