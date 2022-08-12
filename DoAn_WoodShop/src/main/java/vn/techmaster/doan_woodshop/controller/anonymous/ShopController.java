package vn.techmaster.doan_woodshop.controller.anonymous;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.dto.CartProductDTO;
import vn.techmaster.doan_woodshop.model.request.CategoryRep;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.CartService;
import vn.techmaster.doan_woodshop.service.impl.CategoryServiceImpl;
import vn.techmaster.doan_woodshop.service.impl.ProductServiceIpml;
import vn.techmaster.doan_woodshop.utils.AddToCart;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    private CategoryServiceImpl categoryServiceIpml;

    @Autowired
    private ProductServiceIpml productServiceIpml;

    @Autowired private CartService cartService ;

    ModelMapper modelMapper = new ModelMapper();


    @ModelAttribute("categories")
    public List<CategoryRep> getCategories() {
        return categoryServiceIpml.findAll().stream()
                .map(item -> {
                    CategoryRep rep = new CategoryRep();
                    modelMapper.map(item, rep);
                    return rep;
                }).toList();
    }

    @GetMapping()
    public String getAll(Model model,
                         @RequestParam(value = "sortDirection", defaultValue = "ASC") String sortDirection,
                         @RequestParam(value = "sortField", defaultValue = "id") String sortField) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        return listByPage(1, model, "", sort);
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam("keyword") String keyword, Sort sort, String id) {
        return listByPage(1, model, keyword, sort);
    }

    @GetMapping("/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam("keyword") String keyword, Sort sort) {
        int currentPage = pageNumber;

        Page<Product> page;

        Pageable pageable = PageRequest.of(pageNumber - 1, 6, sort);
        page = productServiceIpml.findByNameContaining(keyword, pageable);

        List<Product> productList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productList", productList);
        model.addAttribute("suggestProduct" , productServiceIpml.getListSuggestProduct()) ;
        model.addAttribute("keyword", keyword);

        return "client/shop";
    }

    @GetMapping("/detail/{id}")
    public String DetailProduct(Model model, @PathVariable String id) {
        model.addAttribute("products", productServiceIpml.findById(id).get());
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        return "client/detail";
    }

}
