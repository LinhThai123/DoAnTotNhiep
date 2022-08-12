package vn.techmaster.doan_woodshop.service;

import org.springframework.stereotype.Service;
import vn.techmaster.doan_woodshop.entity.User;

@Service
public interface ChangePasswordService {

    void changePassword (String oldPass,String newPass, Integer userId) ;
}
