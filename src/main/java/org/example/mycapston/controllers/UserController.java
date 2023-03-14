package org.example.mycapston.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.mycapston.models.User;
import org.example.mycapston.repositories.JobRepository;
import org.example.mycapston.repositories.UserRepository;
import org.example.mycapston.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@Slf4j
public class UserController {

    UserRepository userRepository;
    UserService userService;
    JobRepository jobRepository;

    @Autowired
    public UserController(UserRepository userRepository,
                          UserService userService,
                          JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jobRepository = jobRepository;
    }

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @Valid @ModelAttribute User user,
                               BindingResult result, RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/registration";
        }
        try {
            userService.registerUser(user);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("message", "Something went wrong when registration." +e.getMessage());
            return "redirect:/registration";
        }

    }

    @GetMapping("/userProfile")
    public String userProfile(Model model, @RequestParam String email) {
        model.addAttribute("userModel", userService.getUserByEmail(email));
        return "user-profile";
    }



}
