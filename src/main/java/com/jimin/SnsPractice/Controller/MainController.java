package com.jimin.SnsPractice.Controller;

import com.jimin.SnsPractice.Dto.MailDto;
import com.jimin.SnsPractice.Service.EmailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "Main.html";
    }
}
