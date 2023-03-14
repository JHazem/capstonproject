package org.example.mycapston.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mycapston.dto.AddJobDto;
import org.example.mycapston.dto.JobFilterDto;
import org.example.mycapston.models.Job;
import org.example.mycapston.models.Skill;
import org.example.mycapston.services.JobService;
import org.example.mycapston.services.SkillService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/job")
@RequiredArgsConstructor
@Slf4j
public class  JobController {

    private final JobService jobService;
    private final SkillService skillService;


    @GetMapping("/jobList")
    public String jobList(Model model) {
        model.addAttribute("jobList", jobService.jobList());
        return "/admin_templates/job/job-list";
    }

    @GetMapping("/filter-page")
    public String getUserJobs(Model model, @ModelAttribute JobFilterDto jobFilterDto) {
        System.out.println(jobFilterDto);
        model.addAttribute("jobList", jobService.getFilteredJobList(Optional.ofNullable(jobFilterDto.getJobType()),
                Optional.ofNullable(jobFilterDto.getSkillId()), Optional.ofNullable(jobFilterDto.getTitle())));
        model.addAttribute("jobFilterDto", jobFilterDto);
        model.addAttribute("skills", skillService.getAllSkill());
        return "admin_templates/job/job-filter";
    }

    @GetMapping("/add")
    public String addJob(Model model) {
        model.addAttribute("addJobDto", new AddJobDto());
        model.addAttribute("skills", skillService.getAllSkill());
        return "/admin_templates/job/job-add";
    }

    @GetMapping("/update")
    public String updateJob(Model model, @RequestParam long jobId) {
        Job job = jobService.getJobById(jobId);
        Set<Long> skillIds = job.getSkills().stream().map(Skill::getId)
                .collect(Collectors.toSet());

        AddJobDto addJobDto = new AddJobDto();
        addJobDto.setJobId(jobId);
        addJobDto.setJobType(job.getJobType());
        addJobDto.setLocation(job.getLocation());
        addJobDto.setTitle(job.getTitle());
        addJobDto.setCompanyName(job.getCompanyName());
        addJobDto.setSkillIds(skillIds);

        model.addAttribute("addJobDto", addJobDto);
        model.addAttribute("skills", skillService.getAllSkill());
        return "/admin_templates/job/job-add";
    }

    @PostMapping("/addOrUpdate")
    public String addOrUpdate(Model model, @Valid @ModelAttribute AddJobDto addJobDto,
                         BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/job/add";
        }
        try {
            String message = addJobDto.getJobId() != null ? "Job Updated successfully." : "Job Created successfully.";
            jobService.addOrUpdateJob(addJobDto);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", message);

            return "redirect:/job/jobList";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Something went wrong when add job." +e.getMessage());
            return "redirect:/job/add";
        }
    }

    @GetMapping("/delete")
    public String deleteJob(@RequestParam long jobId, RedirectAttributes redirectAttributes) {
        try {
            jobService.deleteJobs(jobId);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Job deleted successfully");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");

            redirectAttributes.addAttribute("message", "Something went wrong when delete job." +e.getMessage());
        }
        return "redirect:/job/jobList";
    }


    @GetMapping("/apply")
    public String applyJob(@RequestParam long jobId, RedirectAttributes redirectAttributes) {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.isAuthenticated()) {
                return "redirect:/login";
            }

            Job job = jobService.getJobById(jobId);
            jobService.applyJob(job, authentication.getName());
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Job applied successfully");

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Something went wrong when apply job." +e.getMessage());
        }
        return "redirect:/job/filter-page";
    }

}
