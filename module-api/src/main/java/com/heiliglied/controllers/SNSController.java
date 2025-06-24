package com.heiliglied.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SNSController {

    @Value("${APP_DOMAIN}")
    private String app_domain;
    @Value("${NAVER_CLIENT_ID}")
    private String naver_client_id;
    @Value("${NAVER_CLIENT_SECRET}")
    private String naver_client_secret;
    @Value("${NAVER_CALLBACK_URI}")
    private String naver_callback_uri;

    @GetMapping("login")
    public ModelAndView getMethodName() {
        ModelAndView mav = new ModelAndView("auth/login");
        return mav;
    }
}
