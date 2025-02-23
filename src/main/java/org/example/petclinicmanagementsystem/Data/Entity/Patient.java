package org.example.petclinicmanagementsystem.Data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "Patient", schema = "public")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EAnimalGender gender;

    @Column(name = "weight")
    private float weight;

    @Column(name = "breed")
    private String breed;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Owner owner;

    public Patient(Long ownerId, String name, LocalDate dateOfBirth, EAnimalGender gender, float weight, String breed, Owner owner) {
        this.ownerId = ownerId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.weight = weight;
        this.breed = breed;
        this.owner = owner;
    }
}
