package com.jwt.authentication.services.implementation;

import com.jwt.authentication.dto.UserDto;
import com.jwt.authentication.entities.Role;
import com.jwt.authentication.entities.User;
import com.jwt.authentication.exceptions.errors.BadRequestException;
import com.jwt.authentication.exceptions.errors.NotFoundException;
import com.jwt.authentication.repositories.UserRepository;
import com.jwt.authentication.services.interfaces.RoleService;
import com.jwt.authentication.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findOne(String username) {
        User userFound= this.userRepository.findByUsername(username);
        if(userFound==null) throw new NotFoundException("User doesn't exists");
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        Optional<User> userFound=this.userRepository.findById(id);
        if(userFound.isEmpty()) throw new NotFoundException("User doesn't exists");
        return  userFound.get();
    }

    @Override
    public User save(UserDto user) {

        User nUser = user.getUserFromDto();
        if(userRepository.existsByEmail(nUser.getEmail()))
            throw new BadRequestException("Email ocupado");
        if(userRepository.findByUsername(user.getUsername())!=null)
            throw new BadRequestException("Already exist user with that username %s".formatted(user.getUsername()));

        nUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        if(nUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        nUser.setRoles(roleSet);
        return userRepository.save(nUser);
    }

}
