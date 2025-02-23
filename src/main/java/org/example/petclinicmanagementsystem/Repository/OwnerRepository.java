package org.example.petclinicmanagementsystem.Repository;

import org.example.petclinicmanagementsystem.Data.Entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository <Owner, Long> {
}
