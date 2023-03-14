package org.example.mycapston;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.example.mycapston.repositories.JobRepository;
import org.example.mycapston.repositories.SkillRepository;
import org.example.mycapston.repositories.UserRepository;
import org.example.mycapston.services.JobService;
import org.example.mycapston.services.SkillService;
import org.example.mycapston.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

public class MyCommandRunner implements CommandLineRunner {


    UserRepository userRepository;
    UserService userService;

    JobRepository jobRepository;
    JobService jobService;

    SkillRepository skillRepository;
    SkillService skillService;


    @Autowired
    public MyCommandRunner(UserRepository userRepository, JobRepository jobRepository,
                           SkillRepository skillRepository,
                           UserService userService) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.userService = userService;
    }


    @PostConstruct
    void created(){
        log.warn("==== My commandLineRunner get Created ===");
    }
    @Override
    public void run(String... args) throws Exception {


    }
}




