package com.vi.StoryHelperAuth.service;

import com.vi.StoryHelperAuth.model.User;
import com.vi.StoryHelperAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public String create(String username, String password) {
        User user = User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .authorities("user")
                .build();

        userRepository.save(user);

        return "User with username = " + username + " was created successfully.";
    }
}
