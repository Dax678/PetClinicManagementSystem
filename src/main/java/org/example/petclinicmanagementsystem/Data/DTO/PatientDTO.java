package org.example.petclinicmanagementsystem.Data.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;
import org.example.petclinicmanagementsystem.Data.Entity.EAnimalGender;

import java.io.Serializable;
import java.time.LocalDate;

@Value
public class PatientDTO implements Serializable {

    @Positive(message = "Id must be a positive number")
    Long id;

    @Positive(message = "Owner Id must be a positive number")
    Long ownerId;

    @NotNull(message = "Name cannot be null")
    String name;

    @NotNull(message = "Date of birth cannot be null")
    LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    EAnimalGender gender;

    @Positive(message = "Weight must be a positive number")
    float weight;

    @NotNull(message = "Breed cannot be null")
    String breed;
}
