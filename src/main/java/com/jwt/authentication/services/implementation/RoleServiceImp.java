package com.jwt.authentication.services.implementation;

import com.jwt.authentication.entities.Role;
import com.jwt.authentication.repositories.RoleRepository;
import com.jwt.authentication.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImp implements RoleService {


    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
