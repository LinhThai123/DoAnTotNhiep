package vn.techmaster.doan_woodshop.controller.admin;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.techmaster.doan_woodshop.entity.Category;
import vn.techmaster.doan_woodshop.entity.Status;
import vn.techmaster.doan_woodshop.model.request.CategoryRep;
import vn.techmaster.doan_woodshop.service.CategoryService;
import vn.techmaster.doan_woodshop.service.impl.CategoryServiceImpl;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("")
    public ModelAndView getAll(ModelMap model) {
        return listByPage(1,model,"");
    }

    @GetMapping(value = "/saveOrEdit")
    public String saveOrEdit(Model model) {
        model.addAttribute("category", new CategoryRep());
        return "admin/categories/form-add-edit-category";
    }

    @RequestMapping(value = "/save", method = {RequestMethod.GET, RequestMethod.POST})
    private ModelAndView AddCategory(@Valid @ModelAttribute("category") CategoryRep rep, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return new ModelAndView("admin/categories/form-add-edit-category");
        }
        Category entity = new Category();
        rep.setUpdated_at(new Date());
        if (rep.getIsCheck()) {
            rep.setCreated_at(categoryService.findById(rep.getId()).get().getCreated_at());
            Optional<Category> dtb = categoryService.findById(rep.getId());
            if (!rep.getName().equalsIgnoreCase(dtb.get().getName())) {
                if (categoryService.existsByName(rep.getName())) {
                    model.addAttribute("message", "Tên danh mục đã tồn tại");
                    return new ModelAndView("forward:/admin/categories", model);
                }
            }
            if (!rep.getCode().equalsIgnoreCase(dtb.get().getCode())) {
                if (categoryService.existsByCode(rep.getCode())) {
                    model.addAttribute("message", "Code danh mục đã tồn tại");
                    return new ModelAndView("forward:/admin/categories", model);
                }
            }
        } else {
            rep.setCreated_at(new Date());
            if (categoryService.existsByNameOrCode(rep.getName(), rep.getCode())) {
                model.addAttribute("message", "Danh mục đã tồn tại");
                return new ModelAndView("forward:/admin/categories", model);
            }
        }
        BeanUtils.copyProperties(rep, entity);
        for (Status s : Status.values()) {
            if (s.name().equals(rep.getStatus())) {
                entity.setStatus(s);
            }
        }
        categoryService.save(entity);
        model.addAttribute("message", "Lưu danh mục thành công");
        return new ModelAndView("forward:/admin/categories", model);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editCategory (ModelMap model , @PathVariable("id") String id ) {
        Optional<Category> category = categoryService.findById(id) ;
        CategoryRep rep = new CategoryRep() ;
        if(category.isPresent()) {
            Category entity = category.get();
            BeanUtils.copyProperties(entity , rep);
            rep.setIsCheck(true);
            model.addAttribute("category" , rep) ;
            return new ModelAndView("admin/categories/form-add-edit-category" , model) ;
        }
        model.addAttribute("message" , "Không tồn tại danh mục này ") ;
        return new ModelAndView("forward:/admin/categories" , model) ;
    }

    @GetMapping(value = "/delete/{id}")
    public ModelAndView deleteCategoryById(@PathVariable("id") String id , ModelMap model) {
        Optional<Category> category = categoryService.findById(id) ;
        Category entity = categoryService.findById(id).get();
        if(category.isPresent()) {
            entity.setStatus(Status.NGUNGBAN);
            entity.setUpdated_at(new Date());
            categoryService.addCategory(entity) ;
            model.addAttribute("message" , "Xóa danh mục thành công") ;
            return new ModelAndView("forward:/admin/categories" , model) ;
        }
        model.addAttribute("mesage" , "Không tìm thấy danh mục này ") ;
        return new ModelAndView("forward:/admin/categories" , model) ;

    }
    @GetMapping("/search")
    public ModelAndView search (ModelMap model , @RequestParam("keyword") String keyword) {
        return listByPage(1, model , keyword) ;
    }
    @RequestMapping("/{pageNumber}")
    public ModelAndView listByPage(@PathVariable("pageNumber") int pageNumber, ModelMap model,
                             @RequestParam("keyword") String keyword){
        int currentPage = pageNumber;

        Page<Category> page ;
        Pageable pageable =  PageRequest.of(pageNumber-1,6);
        page = categoryService.findByNameContaining(keyword,pageable);

        List<Category> categoryList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage",currentPage);
        model.addAttribute("totalItems",totalItems);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("keyword",keyword);
        return new ModelAndView("admin/categories/table-data-category");
    }
}