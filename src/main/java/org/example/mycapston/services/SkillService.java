package org.example.mycapston.services;

import org.example.mycapston.models.Skill;
import org.example.mycapston.models.User;
import org.example.mycapston.repositories.JobRepository;
import org.example.mycapston.repositories.SkillRepository;
import org.example.mycapston.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
public class SkillService {

    UserRepository userRepository;
    UserService userService;

    JobRepository jobRepository;

    SkillRepository skillRepository;

    @Autowired
    public SkillService(UserRepository userRepository,
                        UserService userService, JobRepository jobRepository,
                        SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }


    public User saveSkillToUser(String skillsName) throws Exception {

        if (userRepository.findByEmailAllIgnoreCase(skillsName).isPresent() && skillRepository.findBySkillsNameAllIgnoreCase(skillsName).isPresent()) {
            User user = userRepository.findByEmailAllIgnoreCase(skillsName).get();

            Skill skills = skillRepository.findBySkillsNameAllIgnoreCase(skillsName).get();
            //user.addSkill(skills);

            return user;

        } else {
            throw new Exception("saving a skills to user " + skillsName + " did not null!!");
        }

    }


    public void addSkill(String name) {
        // checking the skill name is present or not.
        skillRepository.findBySkillsNameAllIgnoreCase(name)
                .ifPresent(skill -> {throw new RuntimeException("Skill name is already exist.");});
        Skill skill = new Skill();
        skill.setSkillsName(name);
        skillRepository.save(skill) ;
    }

    public void updateSkill(long skillId, String newSkillName) {
        Skill skill = getSkillById(skillId);
        if (skill.getSkillsName().equalsIgnoreCase(newSkillName)) {
            throw new RuntimeException("You provided same skill name.");
        }
        skill.setSkillsName(newSkillName);
        skillRepository.save(skill);
    }

    public void deleteSkill(long skillId) {
        Skill skillById = getSkillById(skillId);
        skillRepository.delete(skillById);
    }

    public Skill getSkillById(long skillId) {
        return skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with id:" + skillId));
    }

    public List<Skill> getAllSkill() {
        return skillRepository.findAll();
    }

    public List<Skill> getAllSkillByIds(Set<Long> ids) {
        return skillRepository.findAllById(ids);
    }


}
