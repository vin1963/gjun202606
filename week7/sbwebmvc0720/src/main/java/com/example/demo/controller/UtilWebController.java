package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.service.UtilService;

@Controller
@RequestMapping("/web/util")
public class UtilWebController {
    
    private final UtilService utilService;
    private final String appInfo;
    
    public UtilWebController(UtilService utilService, @Qualifier("appInfo") String appInfo) {
        this.utilService = utilService;
        this.appInfo = appInfo;
    }
    
    @GetMapping("/time")
    public String getTime(Model model) {
        model.addAttribute("currentTime", utilService.getCurrentTime());
        model.addAttribute("title", "目前時間");
        return "util/info";
    }
    
    @GetMapping("/uuid")
    public String getUuid(Model model) {
        model.addAttribute("uuid", utilService.generateUuid());
        model.addAttribute("title", "UUID 產生器");
        return "util/info";
    }
    
    @GetMapping("/info")
    public String getAppInfo(Model model) {
        model.addAttribute("appInfo", appInfo);
        model.addAttribute("title", "應用程式資訊");
        return "util/info";
    }
}
