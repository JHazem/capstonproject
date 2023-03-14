package org.example.mycapston.repositories;

import org.example.mycapston.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillsNameAllIgnoreCase(String skillsName);
}

