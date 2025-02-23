package org.example.petclinicmanagementsystem.Data.DTO.Mapper;

import org.example.petclinicmanagementsystem.Data.DTO.PatientDTO;
import org.example.petclinicmanagementsystem.Data.Entity.Patient;

public class PatientMapper {
    public static PatientDTO convertToDTO(Patient patient) {
        if(patient == null) return null;

        return new PatientDTO(
                patient.getId(),
                patient.getOwnerId(),
                patient.getName(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getWeight(),
                patient.getBreed()
        );
    }

    public static Patient convertToEntity(PatientDTO patientDTO) {
        if(patientDTO == null) return null;

        Patient patient = new Patient();
        patient.setId(patientDTO.getId());
        patient.setOwnerId(patientDTO.getOwnerId());
        patient.setName(patientDTO.getName());
        patient.setDateOfBirth(patientDTO.getDateOfBirth());
        patient.setGender(patientDTO.getGender());
        patient.setWeight(patientDTO.getWeight());
        patient.setBreed(patientDTO.getBreed());

        return patient;
    }
}
