package vn.techmaster.doan_woodshop.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendEmail(String email, String subject, String text);
}
