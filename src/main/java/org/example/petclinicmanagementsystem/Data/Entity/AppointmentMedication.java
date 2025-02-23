package org.example.petclinicmanagementsystem.Data.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Appointment_Medication", schema = "public")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class AppointmentMedication {

    @EmbeddedId
    private AppointmentMedicationId id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId(value = "appointmentId")
    @JoinColumn(name = "appointment_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Appointment appointment;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId(value = "medicationId")
    @JoinColumn(name = "medication_id", referencedColumnName = "id", insertable=false, updatable=false)
    @JsonIgnore
    private Medication medication;
}
