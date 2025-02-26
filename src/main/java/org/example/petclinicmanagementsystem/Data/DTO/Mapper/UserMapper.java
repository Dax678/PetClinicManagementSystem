package org.example.petclinicmanagementsystem.Data.DTO.Mapper;

import org.example.petclinicmanagementsystem.Data.DTO.UserDTO;
import org.example.petclinicmanagementsystem.Data.Entity.User;

public class UserMapper {
    public static UserDTO convertToDTO(User user) {
        if(user == null) return null;

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getRole()
        );


    }

    public static User convertToEntity(UserDTO userDTO) {
        if(userDTO == null) return null;

        User user = new User();
        user.setId(user.getId());
        user.setUsername(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setRole(user.getRole());

        return user;
    }
}
