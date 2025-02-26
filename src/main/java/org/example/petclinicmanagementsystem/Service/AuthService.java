package org.example.petclinicmanagementsystem.Service;

import lombok.AllArgsConstructor;
import org.example.petclinicmanagementsystem.Configuration.Exception.ElementAlreadyExistsException;
import org.example.petclinicmanagementsystem.Data.Entity.User;
import org.example.petclinicmanagementsystem.Data.Entity.UserRole;
import org.example.petclinicmanagementsystem.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder encoder;
    JwtService jwtService;

    public String authenticateUser(User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails);
    }

    public String addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            logger.info("User with username: {} already exists", user.getUsername());
            throw new ElementAlreadyExistsException("User with username: {0} already exists", user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            logger.info("User with email: {} already exists", user.getEmail());
            throw new ElementAlreadyExistsException("User with email: {0} already exists", user.getEmail());
        }

        User newUser = new User(
                user.getUsername(),
                encoder.encode(user.getPassword()),
                user.getEmail(),
                UserRole.OWNER.getRoleWithPrefix()
        );

        userRepository.save(newUser);
        return "User registered successfully!";
    }
}
