package org.example.petclinicmanagementsystem.Repository;

import org.example.petclinicmanagementsystem.Data.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
