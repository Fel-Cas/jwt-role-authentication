package com.jwt.authentication.services.implementation;

import com.jwt.authentication.entities.User;
import com.jwt.authentication.services.interfaces.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {
    @Autowired
    private UserService userService;

    public User getCurrentUser(){
        String user= SecurityContextHolder.getContext().getAuthentication().getName();
        return this.userService.findOne(user);
    }
}
