package vn.techmaster.doan_woodshop.controller.admin;

import com.github.slugify.Slugify;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.techmaster.doan_woodshop.entity.Product;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.request.CategoryRep;
import vn.techmaster.doan_woodshop.model.request.ProductRep;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.ProductService;
import vn.techmaster.doan_woodshop.service.impl.CategoryServiceImpl;
import vn.techmaster.doan_woodshop.service.impl.ProductServiceIpml;
import vn.techmaster.doan_woodshop.utils.FileUploadUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/products")
public class ProductController {

    @Autowired
    private CategoryServiceImpl categoryServiceIpml;

    @Autowired
    private ProductService productService;

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
    public String getAll(Model model ) {
        return listByPage(1, model, "");
    }

    @GetMapping("/search")
    public String findProduct (Model model , @RequestParam String keyword) {
        return listByPage(1, model,  keyword );
    }

    @GetMapping("/saveOrEdit")
    public String showSaveOrEditForm(Model model) {
        ProductRep req = new ProductRep();
        req.setIsCheck(false);
        model.addAttribute("product", req);
        return "admin/products/form-add-edit-product";
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(ModelMap model, @Valid @ModelAttribute("product") ProductRep req,
                                    @RequestParam("imageMultipartFile") MultipartFile file,
                                    BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("admin/products/form-add-edit-product") ;
        }
        Product entity = new Product();
        Slugify slg = new Slugify() ;
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if(req.getIsCheck() == false) {
            req.setSlug(slg.slugify(req.getName()));
            req.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            req.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            req.setCreatedBy(user);
            if (!file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                req.setImage(fileName);
                String uploadDir = "product-photos/" + req.getId();
                try {
                    FileUploadUtils.saveFile(uploadDir, fileName, file);
                } catch (IOException e) {
                    throw new IOException("Could not file upload :" + fileName);
                }
            }
        }
        else {
            req.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            req.setSlug(slg.slugify(req.getName()));
            req.setCreatedAt(productService.findById(req.getId()).get().getCreatedAt());
            req.setCreatedBy(productService.findById(req.getId()).get().getCreatedBy());
            req.setModifiedBy(user);
        }
        modelMapper.map(req, entity);
        entity.setCategory(categoryServiceIpml.findById(req.getCategory_id()).get());
        productService.save(entity);
        model.addAttribute("message", "Lưu sản phẩm thành công");
        return new ModelAndView("redirect:/admin/products" , model) ;

    }
    @GetMapping("edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") String id) {
        Optional<Product> product = productService.findById(id);
        Slugify slg = new Slugify() ;
        ProductRep req = new ProductRep();
        if (product.isPresent()) {
            Product entity = product.get();
            BeanUtils.copyProperties(entity, req);
            req.setCategory_id(entity.getCategory().getId());
            req.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            req.setIsCheck(true);
            model.addAttribute("product", req);
            return new ModelAndView("admin/products/form-add-edit-product", model);
        }
        model.addAttribute("message", "Cập nhật thất bại");
        return new ModelAndView("forward:/admin/products", model);
    }
    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(ModelMap model, @PathVariable("id") String id)
            throws IOException {
        Optional<Product> opt = productService.findById(id);
        if (opt.isPresent()) {
            if (!StringUtils.isEmpty(opt.get().getImage())) {
                FileUploadUtils.removeDir(opt.get().getImage());
            }
            productService.delete(opt.get());
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa thất bại");
        }
        return new ModelAndView("forward:/admin/products", model);
    }
    @GetMapping("/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam("keyword") String keyword)
    {
        int currentPage = pageNumber;
        Page<Product> page;

        Pageable pageable = PageRequest.of(pageNumber - 1, 3 );
        page = productService.findByNameContaining(keyword, pageable );
        List<Product> productList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productList", productList);
        model.addAttribute("keyword", keyword);
        return "admin/products/table-data-product";
    }
}
