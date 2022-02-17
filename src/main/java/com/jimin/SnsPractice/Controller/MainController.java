package com.jimin.SnsPractice.Controller;

import com.jimin.SnsPractice.Dto.MailDto;
import com.jimin.SnsPractice.Service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final EmailService emailService;

    public MainController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/mail/send")
    public String main() {
        return "SendMail.html";
    }

    @PostMapping("/mail/send")
    public String sendMail(MailDto mailDto) {
        emailService.sendSimpleMessage(mailDto);
        System.out.println("메일 전송 완료");
        return "AfterMail.html";
    }
}
