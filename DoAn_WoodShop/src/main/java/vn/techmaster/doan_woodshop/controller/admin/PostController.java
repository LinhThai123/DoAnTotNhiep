package vn.techmaster.doan_woodshop.controller.admin;

import com.github.slugify.Slugify;
import org.modelmapper.ModelMapper;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.techmaster.doan_woodshop.entity.Post;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.model.request.PostReq;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.BlogService;
import vn.techmaster.doan_woodshop.utils.FileUploadUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/posts")
public class PostController {

    @Autowired
    private BlogService blogService;

    ModelMapper modelMapper = new ModelMapper() ;

    @GetMapping("")
    public String getBlogPage(Model model) {
        return listByPage(1, model, "");
    }

    @GetMapping("/search")
    public String findBlog(Model model, @RequestParam String keyword) {
        return listByPage(1, model, keyword);
    }

    @GetMapping("/saveOrEdit")
    public String showSaveOrEditForm(Model model) {
        PostReq req = new PostReq();
        req.setIsCheck(false);
        model.addAttribute("post", req);
        return "admin/blog/create";
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(ModelMap model, @Valid @ModelAttribute("post") PostReq req,
                                    @RequestParam("imageMultipartFile") MultipartFile file,
                                    BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("admin/blog/create");
        }
        Post entity = new Post();
        Slugify slg = new Slugify() ;
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (req.getIsCheck() == false) {
            req.setSlug(slg.slugify(req.getTitle()));
            req.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            req.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            req.setPublishedAt(new Timestamp(System.currentTimeMillis()));
            req.setCreatedBy(user);
            if (!file.isEmpty()) {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                req.setThumbnail(fileName);
                String uploadDir = "post-photos/" + req.getId();
                try {
                    FileUploadUtils.saveFile(uploadDir, fileName, file);
                } catch (IOException e) {
                    throw new IOException("Could not file upload :" + fileName);
                }
            }
        }
        else
        {
            req.setModifiedAt(new Timestamp(System.currentTimeMillis()));
            req.setSlug(slg.slugify(req.getTitle()));
            req.setCreatedAt(blogService.findById(req.getId()).get().getCreatedAt());
            req.setPublishedAt(blogService.findById(req.getId()).get().getPublishedAt());
            req.setCreatedBy(blogService.findById(req.getId()).get().getCreatedBy());
            req.setModifiedBy(user);
        }
        modelMapper.map(req, entity);
        blogService.save(entity);
        model.addAttribute("message", "Lưu bài viết thành công");
        return new ModelAndView("redirect:/admin/posts", model);
    }


    @GetMapping("edit/{id}")
    public ModelAndView edit(ModelMap model, @PathVariable("id") String id) {
        Optional<Post> post = blogService.findById(id) ;
        PostReq rep = new PostReq() ;
        if(post.isPresent()) {
            Post entity = post.get();
            modelMapper.map(entity , rep);
            rep.setIsCheck(true);
            model.addAttribute("post" , rep) ;
            model.addAttribute("message" , "Cập nhật thành công");
            return new ModelAndView("admin/blog/create" , model) ;
        }
        model.addAttribute("message" , "Không tồn tại danh mục này ") ;
        return new ModelAndView("forward:/admin/posts" , model) ;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(ModelMap model, @PathVariable("id") String id)
            throws IOException {
        Optional<Post> opt = blogService.findById(id);
        if (opt.isPresent()) {
            if (!StringUtils.isEmpty(opt.get().getThumbnail())) {
                FileUploadUtils.removeDir(opt.get().getThumbnail());
            }
            blogService.delete(opt.get());
            model.addAttribute("message", "Xóa thành công");
        } else {
            model.addAttribute("message", "Xóa thất bại");
        }
        return new ModelAndView("forward:/admin/posts", model);
    }
    @GetMapping("/{pageNumber}")
    public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
                             @RequestParam(value = "keyword", required = false) String keyword) {
        int currentPage = pageNumber;

        Page<Post> page;
        Pageable pageable = PageRequest.of(pageNumber - 1, 10);
        page = blogService.findAllByTitleContains(keyword, pageable);

        List<Post> postList = page.getContent();
        long totalItems = page.getTotalElements();
        int totalPages = page.getTotalPages();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("postList", postList);
        model.addAttribute("keyword", keyword);
        return "admin/blog/list";

    }
}
