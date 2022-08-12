package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.entity.User;
import vn.techmaster.doan_woodshop.exception.NotFoundException;
import vn.techmaster.doan_woodshop.repository.UserRepository;
import vn.techmaster.doan_woodshop.service.ChangePasswordService;

@Component
public class ChangePasswordServiceIpml implements ChangePasswordService {
    @Autowired
    PasswordEncoder passwordEncoder ;

    @Autowired
    UserRepository userRepository ;

    @Override
    public void changePassword(String oldPass, String newPass, Integer userId) {
        User user = userRepository.findById(userId).get() ;
        if(passwordEncoder.matches(oldPass , user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(user);
        }
        else {
            throw new NotFoundException("Mật khẩu cũ không đúng");
        }
    }


}
