package com.jwt.authentication.services.interfaces;

import com.jwt.authentication.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
