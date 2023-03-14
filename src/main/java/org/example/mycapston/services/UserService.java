package org.example.mycapston.services;

import jakarta.transaction.Transactional;
import org.example.mycapston.exceptions.RoleNotFoundException;
import org.example.mycapston.exceptions.UserAlreadyExistException;
import org.example.mycapston.models.Job;
import org.example.mycapston.models.Role;
import org.example.mycapston.models.User;
import org.example.mycapston.repositories.JobRepository;
import org.example.mycapston.repositories.RoleRepository;
import org.example.mycapston.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@Transactional(rollbackOn = Exception.class)
public class UserService {
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, JobRepository jobRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.roleRepository = roleRepository;
    }


    public User registerUser(User user) throws RoleNotFoundException, UserAlreadyExistException {
        if(userRepository.findByEmailAllIgnoreCase(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistException("User is already exist with email:" + user.getEmail());
        }

        Role role = roleRepository.findById(1L)
                .orElseThrow(() -> new RoleNotFoundException("Role is not present with id:" + 1));

        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        user.setRoleList(Collections.singleton(role));

        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAllIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found with mail:"+ email));
    }

    public User saveJobToUser(String email, String title) throws Exception {

        if (userRepository.findByEmailAllIgnoreCase(email).isPresent() && jobRepository.findByTitleAllIgnoreCase(title).isPresent()) {
            User user = userRepository.findByEmailAllIgnoreCase(email).get();
            Job job = jobRepository.findByTitleAllIgnoreCase(title).get();
            user.addJob(job);

            return user;

        } else {
            throw new Exception("saving a job to user " + title + " did not null!!");
        }
    }




}
