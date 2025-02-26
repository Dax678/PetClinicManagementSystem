package org.example.petclinicmanagementsystem.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/owner")
    public String ownerAccess() {
        return "Owner Content.";
    }

    @GetMapping("/veterinarian")
    public String veterinarianAccess() {
        return "Veterinarian Content.";
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin Content.";
    }
}
