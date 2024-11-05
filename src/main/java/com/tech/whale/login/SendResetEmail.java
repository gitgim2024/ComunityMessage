package com.tech.whale.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendResetEmail {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("비밀번호 재설정 링크");
        message.setText("비밀번호 재설정을 위해 다음 링크를 클릭하세요: " + resetLink);
        mailSender.send(message);
    }
}
