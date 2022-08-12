package vn.techmaster.doan_woodshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.doan_woodshop.entity.Cart;
import vn.techmaster.doan_woodshop.entity.Role;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.repository.RoleRepository;
import vn.techmaster.doan_woodshop.service.UserService;
import vn.techmaster.doan_woodshop.utils.FileUploadUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String LoginPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "client/login";
        }
        return "redirect:/client/index";
    }

    @GetMapping("/register")
    public String RegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "client/register";
    }

    @PostMapping("/register/users/save")
    public String saveUser(User user, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        Set<Role> listRole = new HashSet<>();
        listRole.add(roleRepository.findById(2).get());
        user.setRoles(listRole);
        user.setEnabled(true);
        user.setCart(new Cart());

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            User savedUser = userService.saveUser(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        } else {
            userService.saveUser(user);
        }

        return "redirect:/login";
    }

    @GetMapping("admin/users")
    public String getAll(Model model) {
        return listByPage(1, model, "");
    }

    @GetMapping("admin/users/search")
    public String findUser(Model model, @RequestParam String keyword) {
        return listByPage(1, model, keyword);
    }


    @GetMapping("/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam("keyword") String keyword) {
        int currentPage = pageNumber;
        Page<User> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 3);
        page = userService.findByFirstNameContaining(keyword, pageable);
        List<User> userList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("userList", userList);
        model.addAttribute("keyword", keyword);
        return "admin/users/table-data-user";
    }
}
