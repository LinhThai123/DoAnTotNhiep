package vn.techmaster.doan_woodshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import vn.techmaster.doan_woodshop.service.MailService;

@Component
public class MailServiceIpml implements MailService {

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        // Send Message!
        sender.send(message);
    }
}
