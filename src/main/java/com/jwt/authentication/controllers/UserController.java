package com.jwt.authentication.controllers;

import com.jwt.authentication.config.JwtAuthenticationFilter;
import com.jwt.authentication.config.TokenProvider;
import com.jwt.authentication.dto.AuthToken;
import com.jwt.authentication.dto.LoginUser;
import com.jwt.authentication.dto.PasswordDTO;
import com.jwt.authentication.dto.UserDto;
import com.jwt.authentication.entities.Role;
import com.jwt.authentication.entities.User;
import com.jwt.authentication.exceptions.errors.ForbiddenException;
import com.jwt.authentication.services.implementation.CurrentUser;
import com.jwt.authentication.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CurrentUser currentUser;


   @PostMapping("/register")
    public User saveUser(@RequestBody UserDto user){
        return userService.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<User>> getAll(){
       List<User> users=this.userService.findAll();
       return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
       User currentuser=this.currentUser.getCurrentUser();
       if(currentuser.getId()!=id && !isAdmin(currentuser)) throw new ForbiddenException("You can't view this user");
       return  ResponseEntity.ok(this.userService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable Long id){
        this.userService.delete(id);
       return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping ("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserDto userDto){
        User currentuser=this.currentUser.getCurrentUser();
        if(currentuser.getId()!=id && !isAdmin(currentuser)) throw new ForbiddenException("You can't view this user");
        return  ResponseEntity.ok(this.userService.update(id,userDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PutMapping ("/password/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody PasswordDTO passwordDTO){
        User currentuser=this.currentUser.getCurrentUser();
        if(currentuser.getId()!=id) throw new ForbiddenException("You can't update user's password");
        this.userService.updatePassword(id,passwordDTO);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-admin")
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello-admin-user")
    public String adminUser(){
        return "Only Admins and Users Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hello-user")
    public String userPing(){
        return "Any User Can Read This";
    }

    protected  boolean isAdmin(User user){
        List<Role>roles= (List<Role>) user.getRoles().stream().filter(role->role.getName()=="ADMIN").collect(Collectors.toList());
        return roles.size()==1;
    }


}
