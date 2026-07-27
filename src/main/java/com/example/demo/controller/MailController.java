package com.example.demo.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MailController {

    private final JavaMailSender javaMailSender;

    @NonFinal
    @Value("${spring.mail.username}")
    protected String mail;

    @PostMapping("/forgot-password/{mailTarget}")
    public String testSendMail(@PathVariable String mailTarget) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(mailTarget);
        simpleMailMessage.setFrom(mail);
        simpleMailMessage.setSubject("Test hệ thống gửi mail");
        simpleMailMessage.setText("Nếu bạn nhận được mail này, cấu hình SMTP của bạn đã hoạt động hoàn hảo!");

        javaMailSender.send(simpleMailMessage);

        return "Đã gửi mail test thành công tới: " + mailTarget;
    }


}
