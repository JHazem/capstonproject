package org.example.mycapston.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.mycapston.models.Skill;
import org.example.mycapston.services.SkillService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/skill")
public class SkillController {

    private final SkillService skillService;

    @GetMapping
    public String getSkillList(Model model) {
        model.addAttribute("skill", new Skill());
        model.addAttribute("skillList", skillService.getAllSkill());
        return "admin_templates/skill/skill-list";
    }

    @GetMapping("/add")
    public String addSkill(Model model) {
        model.addAttribute("skill", new Skill());
        return "admin_templates/skill/skill-add";
    }

    @PostMapping("/add")
    public String addSkill(@ModelAttribute Skill skill,
                            BindingResult result,
                            RedirectAttributes redirectAttributes){

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/skill/add";
        }

        try {
            skillService.addSkill(skill.getSkillsName());
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Skill added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to add Skill,"+e.getMessage());
        }

        return "redirect:/skill";
    }

    @PostMapping("/update")
    public String updateSkill(
            @ModelAttribute @Valid Skill skill,
            BindingResult result,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to Validate form.");
            return "redirect:/skill/update";
        }

        try {

            skillService.updateSkill(skill.getId(), skill.getSkillsName());

            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Skill Updated successfully.");
            return "redirect:/skill";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed, "+e.getMessage());
            return "redirect:/skill";
        }
    }


    @GetMapping("/delete")
    public String deleteSkill(@RequestParam long skillId, RedirectAttributes redirectAttributes) {
        try {
            skillService.deleteSkill(skillId);
            redirectAttributes.addAttribute("messageType", "Success");
            redirectAttributes.addAttribute("class", "bg-success");
            redirectAttributes.addAttribute("message", "Skill Deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("messageType", "Failed");
            redirectAttributes.addAttribute("class", "bg-danger");
            redirectAttributes.addAttribute("message", "Failed to delete Skill,"+e.getMessage());
        }
        return "redirect:/skill";
    }

}
