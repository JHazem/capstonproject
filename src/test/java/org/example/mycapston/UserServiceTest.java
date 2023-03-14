package org.example.mycapston;

import org.example.mycapston.exceptions.RoleNotFoundException;
import org.example.mycapston.exceptions.UserAlreadyExistException;
import org.example.mycapston.models.User;
import org.example.mycapston.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    public UserService userService;

    /**
     *
     * @throws UserAlreadyExistException
     * @throws RoleNotFoundException
     *
     * Creating user without exception
     *
     */
    @Test
    public void testUserRegistration() throws UserAlreadyExistException, RoleNotFoundException {


        User user = getUserObject("test3@test.com");
        user.setPassword(new BCryptPasswordEncoder().encode("1234"));

        User savedUser = userService.registerUser(user);

        assertEquals(savedUser.getEmail(), "test3@test.com");

    }

    /**
     *
     * @throws UserAlreadyExistException
     * @throws RoleNotFoundException
     *
     * Creating user without exception
     *
     */
    @Test
    public void testUserRegistrationFailureWithUserAlreadyExist() {
        User user = getUserObject("test3@test.com");
        UserAlreadyExistException userAlreadyExistException = assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(user));

        assertEquals("User is already exist with email:"+user.getEmail(), userAlreadyExistException.getMessage());
    }



    public User getUserObject(String email) {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("Test2");
        user.setEmail(email);
        user.setPhoneNumber("0129839009");
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setCredentialsNonExpired(true);

        return user;
    }


}
