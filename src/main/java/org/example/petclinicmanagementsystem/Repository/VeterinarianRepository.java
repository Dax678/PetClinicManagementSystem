package org.example.petclinicmanagementsystem.Repository;

import org.example.petclinicmanagementsystem.Data.Entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
