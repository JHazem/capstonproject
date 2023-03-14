package org.example.mycapston.controllers;

import org.example.mycapston.dto.JobFilterDto;
import org.example.mycapston.services.JobService;
import org.example.mycapston.services.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final JobService jobService;
    private final SkillService skillService;

    public HomeController(JobService jobService, SkillService skillService) {
        this.jobService = jobService;
        this.skillService = skillService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("jobList", jobService.jobList());
        model.addAttribute("skills", skillService.getAllSkill());
        model.addAttribute("jobFilterDto", new JobFilterDto());
        return "admin_templates/job/job-filter";
    }
}
