package org.example.mycapston.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/loginError")
    public String getLoginErrorPage(Model model){
        model.addAttribute("loginError", true);
        return "login";
    }


}
