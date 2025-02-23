package org.example.petclinicmanagementsystem.Repository;

import org.example.petclinicmanagementsystem.Data.Entity.AppointmentMedication;
import org.example.petclinicmanagementsystem.Data.Entity.AppointmentMedicationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentMedicationRepository extends JpaRepository<AppointmentMedication, AppointmentMedicationId> {
}
