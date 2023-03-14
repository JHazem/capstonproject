package org.example.mycapston.repositories;

 import jakarta.transaction.Transactional;
 import org.example.mycapston.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
 import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    Optional<Job> findByTitleAndCompanyNameAndLocationAndJobTypeAllIgnoreCase(
            String title, String companyName, String location, String jobType);

    Optional<Job> findByTitleAllIgnoreCase(String title);

}

