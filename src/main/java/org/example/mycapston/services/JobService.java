package org.example.mycapston.services;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.example.mycapston.dto.AddJobDto;
import org.example.mycapston.models.Job;
import org.example.mycapston.models.Skill;
import org.example.mycapston.models.User;
import org.example.mycapston.repositories.JobRepository;
import org.example.mycapston.repositories.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@Transactional(rollbackOn = Exception.class)
public class JobService {


    private UserRepository userRepository;
    private UserService userService;
    private SkillService skillService;

    private JobRepository jobRepository;

    public JobService(UserRepository userRepository, UserService userService,
                      SkillService skillService, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.skillService = skillService;
        this.jobRepository = jobRepository;
    }

    public void addOrUpdateJob(AddJobDto addJobDto) {
        List<Skill> skills = skillService.getAllSkillByIds(addJobDto.getSkillIds());

        Job job = new Job();
        // if id is present, update it otherwise new job will be created
        if (addJobDto.getJobId() != null) {
            job = getJobById(addJobDto.getJobId());
        }
        job.setSkills(new HashSet<>(skills));
        job.setCompanyName(addJobDto.getCompanyName());
        job.setJobType(addJobDto.getJobType());
        job.setTitle(addJobDto.getTitle());
        job.setLocation(addJobDto.getLocation());

        jobRepository.save(job);
    }

    public List<Job> jobList() {
        return jobRepository.findAll();
    }

    public Job getJobById(long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with id:"+ jobId));
    }

    public Set<Job> applyJob(final Job job, String userEmail) throws Exception {

        if (userRepository.findByEmailAllIgnoreCase(userEmail).isPresent()) {
            User user = userRepository.findByEmailAllIgnoreCase(userEmail).get();

            // checking if the user is already apply to this job.
            Optional<Job> optionalJob = user.getJobs().stream().filter(job1 -> Objects.equals(job1.getJobId(), job.getJobId()))
                    .findFirst();
            if (optionalJob.isPresent()) {
                throw new RuntimeException("You already applied to this Job");
            }

            user.addJob(job);
            user = userRepository.saveAndFlush(user);
            return user.getJobs();

        } else {
            throw new Exception("saving a job to user " + userEmail + " did not null!!");
        }
    }
        public void deleteJobs(long id) throws Exception {
            Optional<Job> wantToDelete = jobRepository.findById(id);

            if(wantToDelete.isPresent()){
                jobRepository.delete(wantToDelete.get());
            } else {
                throw new Exception("Can't find the job" + wantToDelete);

        }
    }

    public List<Job> getFilteredJobList(Optional<String> jobType, Optional<Long> skillId, Optional<String> title) {
        return jobRepository.findAll(jobFilterSpec(jobType, skillId, title));
    }

    static Specification<Job> jobFilterSpec(Optional<String> jobType, Optional<Long> skillId, Optional<String> title) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            jobType.ifPresent(s -> predicates.add(builder.like(root.get("jobType"), s)));

            title.ifPresent(s -> predicates.add(builder.like(root.get("title"), s)));

            skillId.ifPresent(cn -> {
                Join<Job, Skill> nestedJoin = root.join("skills");
                predicates.add(builder.equal(nestedJoin.get("id"), cn));
            });
            // AND all predicates
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
