package org.example.petclinicmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PetClinicManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetClinicManagementSystemApplication.class, args);
    }

}
