package com.jimin.SnsPractice.Controller;

import com.jimin.SnsPractice.Dto.MailDto;
import com.jimin.SnsPractice.Service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class MailController {
    private final EmailService emailService;

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
