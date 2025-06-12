package com.heiliglied.app.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequiredArgsConstructor
@RequestMapping("scheduler")
public class ScheduleController {

    @PostMapping("authTest")
    public Map<String, Object> authTest(@RequestBody Map<String, Object> data) {
        Map<String, Object> result = new HashMap<>();

        result.put("status", "success");
        result.put("msg", "연결 성공");

        return result;
    }
    
}
