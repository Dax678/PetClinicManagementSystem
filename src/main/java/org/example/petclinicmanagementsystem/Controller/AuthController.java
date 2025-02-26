package org.example.petclinicmanagementsystem.Controller;

import lombok.AllArgsConstructor;
import org.example.petclinicmanagementsystem.Data.Entity.User;
import org.example.petclinicmanagementsystem.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User user) {
        String response = authService.authenticateUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("token", response));
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        String response = authService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", response));
    }
}
