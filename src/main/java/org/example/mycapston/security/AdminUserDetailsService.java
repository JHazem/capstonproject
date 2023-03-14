package org.example.mycapston.security;

import lombok.RequiredArgsConstructor;
import org.example.mycapston.models.User;
import org.example.mycapston.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final UserRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUsers = usersRepository.findByEmailAllIgnoreCase(email);
        User users = optionalUsers.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(users);
    }

    static class CustomUserDetails extends User implements UserDetails{

        private User user;

        public CustomUserDetails(User user) {
           super(user);
            this.user = user;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
            super.getRoleList().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
            return grantedAuthorities;
        }

        public String getUsername() {
            return user.getEmail();
        }
    }
}
