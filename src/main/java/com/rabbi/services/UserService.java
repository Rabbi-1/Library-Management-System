package com.rabbi.services;

import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;

import java.util.List;

public interface UserService {

    public User getCurrentUser() throws Exception;
    public List<UserDTO> getAllUsers();
    User findById(Long id) throws Exception;

}
