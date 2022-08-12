package vn.techmaster.doan_woodshop.controller.anonymous;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.techmaster.doan_woodshop.entity.Feeback;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.request.CategoryRep;
import vn.techmaster.doan_woodshop.model.request.ContactReq;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.CartService;
import vn.techmaster.doan_woodshop.service.ContactService;
import vn.techmaster.doan_woodshop.utils.FileUploadUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contact")
public class ContactController {

    @Autowired private ContactService contactService ;

    @Autowired private CartService cartService ;

    ModelMapper modelMapper = new ModelMapper();

    @GetMapping("")
    public String getContactPage(Model model) {
        return listByPage(1, model, "");
    }

    @GetMapping("/search")
    public String findContact(Model model, @RequestParam String keyword) {
        return listByPage(1, model, keyword);
    }

    @GetMapping(value = "/saveOrEdit")
    public String saveOrEdit(Model model) {
        model.addAttribute("contact" , new ContactReq()) ;
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("countCart", cartService.countProducts(user.getCart().getId())) ;
        return "client/contact";
    }

    @RequestMapping( value = "/save" , method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView createContact (@Valid @ModelAttribute("contact") ContactReq req , ModelMap model ,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("client/contact");
        }
        Feeback fe = new Feeback() ;
        req.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        modelMapper.map(req, fe);
        contactService.save(fe) ;
        model.addAttribute("message", "Gửi liên hệ thành công");
        return new ModelAndView("redirect:contact/saveOrEdit" , model) ;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(ModelMap model, @PathVariable("id") String id)
            throws IOException {
        Optional<Feeback> opt = contactService.findById(id);
        if (opt.isPresent()) {
            contactService.delete(opt.get());
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa thất bại");
        }
        return new ModelAndView("forward:/contact", model);
    }
    @GetMapping("/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        int currentPage = pageNumber;

        Page<Feeback> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);
        page = contactService.findAllByTitleContains(keyword, pageable);

        List<Feeback> contactList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("contactList", contactList);
        model.addAttribute("keyword", keyword);
        return "admin/contact/list";

    }
}
