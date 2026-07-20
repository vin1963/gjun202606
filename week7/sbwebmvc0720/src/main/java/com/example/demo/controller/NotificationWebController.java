package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.service.NotificationService;

@Controller
@RequestMapping("/web/notification")
public class NotificationWebController {
    
    private final NotificationService emailService;
    private final NotificationService smsService;
    
    public NotificationWebController(
            @Qualifier("emailNotificationService") NotificationService emailService,
            @Qualifier("smsNotificationService") NotificationService smsService) {
        this.emailService = emailService;
        this.smsService = smsService;
    }
    
    @GetMapping("/email")
    public String sendEmail(Model model) {
        String result = emailService.sendNotification("這是一封電子郵件");
        model.addAttribute("result", result);
        model.addAttribute("type", "電子郵件");
        return "notification/result";
    }
    
    @GetMapping("/sms")
    public String sendSms(Model model) {
        String result = smsService.sendNotification("這是一條簡訊");
        model.addAttribute("result", result);
        model.addAttribute("type", "簡訊");
        return "notification/result";
    }
}
