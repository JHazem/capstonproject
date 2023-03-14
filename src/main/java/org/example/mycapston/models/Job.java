package org.example.mycapston.models;

import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Entity

@Getter
@Setter
@ToString
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "jobs")
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long jobId;


    String title;


    String companyName;

    String location;

    String jobType;

    public Job(String title, String companyName, String location, String jobType) {
        this.title = title;
        this.companyName = companyName;
        this.location = location;
        this.jobType = jobType;
    }

    public Job(Long jobId, String title, String companyName, String location, String jobType) {
        this.jobId = jobId;
        this.title = title;
        this.companyName = companyName;
        this.location = location;
        this.jobType = jobType;
    }

    @ToString.Exclude
    @ManyToMany(mappedBy = "jobs", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    List<User> user = new ArrayList<>();

    @ManyToMany
    Set<Skill> skills;


    public void addUser(User u){
        user.add(u);
        u.getJobs().add(this);
        log.debug("add user executed");
    }


    public void removeUser(User u){
        user.remove(u);
        u.getJobs().remove(this);
        log.debug("remove user executed");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return Objects.equals(title, job.title) && companyName.equals(job.companyName) && Objects.equals(location, job.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, companyName, location);
    }
}
