package com.vi.StoryHelperAuth.rest;

import com.vi.StoryHelperAuth.model.User;
import com.vi.StoryHelperAuth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserResource {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.loadUserByUsername(username);
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        if (passwordEncoder.matches(password, user.getPassword()))
            return ResponseEntity.ok()
                    .body(User.builder()
                            .username(user.getUsername())
                            .authorities(user.getAuthorities().stream().map(el -> el.getAuthority()).collect(Collectors.joining("&")))
                            .build());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    @PostMapping("/create")
    public String create(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.create(username, password);
    }
}
