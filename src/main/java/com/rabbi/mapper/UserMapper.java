package com.rabbi.mapper;

import com.rabbi.model.User;
import com.rabbi.payload.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserMapper {
    //stuff to be sent to the frontend and is visible
    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhone(user.getPhone());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setRole(user.getRole());


        return userDTO;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
    public static Set<UserDTO> toDTOSet(Set<User> users) {
        return users
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toSet());
    }

    public static User toEntity(UserDTO userDTO) {
        User createdUser = new User();
        createdUser.setEmail(userDTO.getEmail());
        createdUser.setPassword(userDTO.getPassword());
        createdUser.setCreatedAt(LocalDateTime.now());
        createdUser.setPhone(userDTO.getPhone());
        createdUser.setFullName(userDTO.getFullName());
        createdUser.setRole(userDTO.getRole());
        return createdUser;
    }
}



//{
//        "id": 12,
//        "email": "user@example.com",
//        "fullName": "John Doe",
//        "phone": "555-1234",
//        "lastLogin": "2026-01-04T14:22:10",
//        "role": "USER"
//}
