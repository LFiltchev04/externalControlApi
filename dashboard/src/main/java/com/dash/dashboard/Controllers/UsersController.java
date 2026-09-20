package com.dash.dashboard.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dash.dashboard.Services.UserService;

@RestController
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;
    public UsersController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String createUser(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
     
        
        userService.createUser(userId);
        return "User created with ID: " + userId;

    }

    
}
