package vn.techmaster.doan_woodshop.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.exception.NotFoundException;
import vn.techmaster.doan_woodshop.model.request.ChangePasswordReq;
import vn.techmaster.doan_woodshop.security.CustomUserDetails;
import vn.techmaster.doan_woodshop.service.ChangePasswordService;
import vn.techmaster.doan_woodshop.service.ForgotPasswordService;
import vn.techmaster.doan_woodshop.service.UserService;
import vn.techmaster.doan_woodshop.utils.FileUploadUtils;

import java.io.IOException;

@Controller
public class AccountController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private ChangePasswordService changePasswordService ;

    @GetMapping("/account")
    public String viewDetailAccount(@AuthenticationPrincipal CustomUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = userService.getByEmail(email);
        model.addAttribute("user", user);
        return "admin/users/user-details";
    }

    @PostMapping("/account/update")
    public String updateUser(User user, RedirectAttributes re, @AuthenticationPrincipal CustomUserDetails loggedUser,
                             @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            User savedUser = userService.updateAccount(user);

            String uploadDir = "user-photos/" + savedUser.getId();

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            userService.updateAccount(user);
        }
        loggedUser.setFirstName(user.getFirstName());
        loggedUser.setLastName(user.getLastName());

        re.addFlashAttribute("message", "Your account details have been updated.");

        return "redirect:/account";
    }

    @GetMapping("/quen-mat-khau")
    public String getFogotPassword() {
        return "client/reset_password";
    }

    @PostMapping("/forgot-request")
    public String regetPassword(@ModelAttribute("email") String email) {
        forgotPasswordService.forgotPass(email);
        return "redirect:/login";
    }

    @GetMapping("/doi-mat-khau")
    public String changePassword (Model model) {
        model.addAttribute("changePassword" , new ChangePasswordReq()) ;
        return "client/change_password" ;
    }
    @PostMapping("/change-password")
    public String changePasswords(@ModelAttribute("changePassword") ChangePasswordReq req , Model model ,
                                  BindingResult result) {
        if (result.hasErrors()) {
            return "client/change_password" ;
        }
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        if(req.getNewpassword().equals(req.getRetypepassword())) {
            changePasswordService.changePassword(req.getOldpassword() , req.getNewpassword() , user.getId());
        }
        else {
            result.addError(new FieldError("password", "retypePass", "Mật khẩu không giống nhau !"));
            return "client/change_password" ;
        }
        return "redirect:/account" ;
    }
}
