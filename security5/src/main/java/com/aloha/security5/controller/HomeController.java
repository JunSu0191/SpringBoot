package com.aloha.security5.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import com.aloha.security5.dto.Users;
import com.aloha.security5.service.UserService;




@Slf4j
@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    
    /**
     * 메인 화면
     * @param model
     * @param Principal
     * @return
     */
    @GetMapping({"", "/"})
    public String home(Model model, Principal Principal) {
        // Principal : 현재 로그인 한 사용자 정보를 확인하는 인터페이스
        String loginId = Principal != null ? Principal.getName() : "guest";
        model.addAttribute("loginId", loginId);
        return "index";
    }

    /**
     * 인증 예외 페이지 (접근 거부 에러 페이지)
     * @param auth
     * @param model
     * @return
     */
    @GetMapping("/exception")
    public String exception(Authentication auth, Model model) {
        log.info("인증 예외 처리...");
        log.info("auth : " + auth.toString());
        model.addAttribute("msg", "인증 거부 : " + auth.toString());
        return "/exception";
    }
    
    /**
     * 로그인 화면
     * @return
     */
    @GetMapping("/login")
    public String login() {
        log.info("로그인 페이지...");
        return "/login";
    }
    
    @GetMapping("/join")
    public String join() {
        return "/join";
    }

    @PostMapping("/join")
    public String joinPro(Users user) throws Exception {
        
        int result = userService.join(user);
        if (result > 0 ) {
            return "redirect:/login";
        }
        
        return "redirect:/join?error" ;
    }

}