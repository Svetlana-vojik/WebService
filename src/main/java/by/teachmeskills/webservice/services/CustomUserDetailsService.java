package by.teachmeskills.webservice.services;

import by.teachmeskills.webservice.entities.CustomUserDetails;
import by.teachmeskills.webservice.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userService.findByLogin(username);
        return user.map(CustomUserDetails::fromUserEntityToCustomUserDetails).orElse(null);
    }
}