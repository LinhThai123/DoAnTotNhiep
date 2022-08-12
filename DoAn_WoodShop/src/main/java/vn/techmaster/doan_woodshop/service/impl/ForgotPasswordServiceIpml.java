package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.repository.UserRepository;
import vn.techmaster.doan_woodshop.service.ForgotPasswordService;
import vn.techmaster.doan_woodshop.service.MailService;
import vn.techmaster.doan_woodshop.service.UserService;

import java.util.UUID;

@Component
public class ForgotPasswordServiceIpml implements ForgotPasswordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService ;

    @Autowired
    private UserService userService ;

    @Autowired private PasswordEncoder passwordEncoder ;

    @Override
    public void forgotPass(String email) {
        User user = userRepository.getUserByEmail(email);
        String token = UUID.randomUUID().toString() ;
        user.setPassword(passwordEncoder.encode(token));
        userRepository.save(user);
        mailService.sendEmail(email,"Đổi mật khẩu" , "Mật khẩu mới của bản là :" + token);
    }

}
