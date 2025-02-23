package org.example.petclinicmanagementsystem;

import org.springframework.boot.SpringApplication;

public class TestPetClinicManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.from(PetClinicManagementSystemApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
