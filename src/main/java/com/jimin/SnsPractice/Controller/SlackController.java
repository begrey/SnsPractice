package com.jimin.SnsPractice.Controller;

import com.jimin.SnsPractice.Service.SlackBotService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class SlackController {
    private final SlackBotService slackBotService;

    @GetMapping("/dm/send")
    public String main() {
        return "SendDm.html";
    }

    @PostMapping("/dm/send")
    public String sendMail(String intraId) {
        slackBotService.sendPhotoToUser(intraId);
        slackBotService.sendMessageToUser(intraId);
        System.out.println("DM 전송 완료");
        return "AfterMail.html";
    }
}
