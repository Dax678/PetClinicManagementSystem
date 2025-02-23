package org.example.petclinicmanagementsystem.Data.Entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AppointmentMedicationId implements Serializable {
    private Long appointmentId;
    private Long medicationId;
}
