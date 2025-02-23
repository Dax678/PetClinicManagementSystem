package org.example.petclinicmanagementsystem.Repository;

import org.example.petclinicmanagementsystem.Data.Entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
