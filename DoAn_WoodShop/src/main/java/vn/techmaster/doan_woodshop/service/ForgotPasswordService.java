package vn.techmaster.doan_woodshop.service;

import org.springframework.stereotype.Service;

@Service
public interface ForgotPasswordService {
    public abstract void forgotPass(String email);
}
