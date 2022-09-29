package com.jwt.authentication.services.interfaces;

import com.jwt.authentication.dto.UserDto;
import com.jwt.authentication.entities.User;
import com.jwt.authentication.exceptions.errors.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findOne(String username);
    User findById(Long id) throws NotFoundException;
    void delete(Long id) throws NotFoundException;
    User update(Long id, UserDto userDto) throws NotFoundException;
}
