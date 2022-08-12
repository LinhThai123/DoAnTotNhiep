package vn.techmaster.doan_woodshop.controller.anonymous;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.techmaster.doan_woodshop.entity.*;
import vn.techmaster.doan_woodshop.repository.CartProductRepository;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.CartService;
import vn.techmaster.doan_woodshop.service.OrderItemService;
import vn.techmaster.doan_woodshop.service.UserService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/checkout")

public class CheckoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("")
    public String checkout(Model model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("carts", cartProductRepository.findByCartId(user.getCart().getId()));
        model.addAttribute("total", cartService.cartProducts(user.getCart().getId()));
        model.addAttribute("users", user);
        model.addAttribute("checkout", new OrderItem());
        model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        return "client/checkout";
    }

    @PostMapping("/save")
    public ModelAndView createOrderItem(@ModelAttribute("checkout") OrderItem orderItem, ModelMap model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderItem.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        orderItem.setStatus(1);
        orderItem.setUser(user);

        OrderItem orderItemSave = orderItemService.save(orderItem);

        for (CartProduct cartProduct : user.getCart().getCartProducts()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(cartProduct.getQuantity());
            orderDetail.setProduct(cartProduct.getProduct());
            orderDetail.setTotalPrice(cartProduct.getQuantity() * cartProduct.getProduct().getPrice());
            orderDetail.setOrder_item(orderItemSave);
            orderDetails.add(orderDetail);
            cartProductRepository.delete(cartProduct);
        }
        orderItemSave.setOrderDetails(orderDetails);
        orderItemService.save(orderItemSave);
        model.addAttribute("message", "Đặt hàng thành công");
        return new ModelAndView("redirect:/checkout" , model);
    }
}
