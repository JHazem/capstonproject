package org.example.mycapston.models;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)

@Table(name = "skills")
public class Skill {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Size(max = 50)
    String skillsName;


    public Skill(@NonNull String skillsName, Set<User> user) {
        this.skillsName = skillsName;
        //this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return skillsName.equals(skill.skillsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skillsName);
    }
}
