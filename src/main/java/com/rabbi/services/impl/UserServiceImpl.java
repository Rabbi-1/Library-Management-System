package com.rabbi.services.impl;

import com.rabbi.mapper.UserMapper;
import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;
import com.rabbi.repo.UserRespository;
import com.rabbi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRespository userRespository;

    @Override
    public User getCurrentUser() throws Exception {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRespository.findByEmail(email);
        if (user == null) {
            throw new Exception("User not found");
        }
        return user;

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRespository.findAll();
        return users.stream().map(
                UserMapper::toDTO
        ).collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRespository.findById(id).orElseThrow(
                () -> new Exception("User not found with give id")
        );
    }
}
