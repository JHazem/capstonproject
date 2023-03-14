package org.example.mycapston.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;



    String firstName;

    String lastName;

    String email;

    String password;

    @Size(max = 15)
    String phoneNumber;


    @Column(nullable = false)
    private boolean accountNonExpired;

    @Column(nullable = false)
    private boolean accountNonLocked;

    @Column(nullable = false)
    private boolean credentialsNonExpired;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "USERS_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            foreignKey = @ForeignKey(name = "USERS_ROLE_FK"),
            inverseForeignKey = @ForeignKey(name = "ROLE_USERS_FK"))
    private Set<Role> roleList;

    public User(User users) {
        this.id = users.getId();
        this.password = users.getPassword();
        this.email = users.getEmail();
        this.accountNonExpired = users.isAccountNonExpired();
        this.accountNonLocked = users.isAccountNonLocked();
        this.credentialsNonExpired = users.isCredentialsNonExpired();
        this.enabled = users.isEnabled();
        this.roleList = users.getRoleList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User users = (User) o;
        return id == users.id && email.equals(users.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }





    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "user_job",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    Set<Job> jobs = new LinkedHashSet<>();

    public void addJob(Job j){
        jobs.add(j);
        j.getUser().add(this);
        log.debug("add jobs executed");
    }


    public void removeJob(Job j){
        jobs.remove(j);
        j.getUser().remove(this);
        log.debug("remove jobs executed");
    }


}
