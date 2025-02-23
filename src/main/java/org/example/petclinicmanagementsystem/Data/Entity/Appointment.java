package org.example.petclinicmanagementsystem.Data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Appointment", schema = "public")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "vet_id")
    private Long vetId;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vet_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Veterinarian vet;

    @OneToMany
    @JsonIgnore
    private List<AppointmentMedication> medications;
}
