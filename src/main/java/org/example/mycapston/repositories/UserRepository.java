package org.example.mycapston.repositories;

import jakarta.transaction.Transactional;
import org.example.mycapston.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(rollbackOn = Exception.class)
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFirstNameAndLastNameAndEmailAndPhoneNumberAllIgnoreCase(
            String firstName,String  lastName,String  email, String phoneNumber);

    Optional<User> findByEmailAllIgnoreCase(String email);
    List<User> findByLastNameAllIgnoreCase(String lastName);


}

