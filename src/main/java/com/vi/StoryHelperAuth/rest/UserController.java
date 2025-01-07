package com.vi.StoryHelperAuth.rest;

import com.vi.StoryHelperAuth.model.User;
import com.vi.StoryHelperAuth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(
            value = "/login",
            produces = "application/json")
    public ResponseEntity<User> login() {
        User user = userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        return ResponseEntity.ok()
                .body(User.builder()
                        .username(user.getUsername())
                        .authorities(user.getAuthorities().stream().map(el -> el.getAuthority()).collect(Collectors.joining("&")))
                        .build());
    }

    @PostMapping(
            value = "/create",
            produces = "application/json")
    public String create(@RequestParam("username") String username, @RequestParam("password") String password) {
        return userService.create(username, password);
    }
}
