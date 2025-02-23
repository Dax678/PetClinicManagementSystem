package org.example.petclinicmanagementsystem.Service;

import lombok.AllArgsConstructor;
import org.example.petclinicmanagementsystem.Configuration.Exception.ElementNotFoundException;
import org.example.petclinicmanagementsystem.Data.DTO.Mapper.PatientMapper;
import org.example.petclinicmanagementsystem.Data.DTO.PatientDTO;
import org.example.petclinicmanagementsystem.Data.Entity.Patient;
import org.example.petclinicmanagementsystem.Repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository patientRepository;

    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();

        if(patients.isEmpty()) {
            logger.warn("No patients found");
            throw new ElementNotFoundException("No patients found");
        }

        return patients.stream()
                .map(PatientMapper::convertToDTO)
                .toList();
    }

    public PatientDTO getPatientById(long id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isEmpty()) {
            logger.info("Patient with id: {} not found", id);
            throw new ElementNotFoundException("Patient with id: {0} not found", id);
        }

        return PatientMapper.convertToDTO(patient.get());
    }
}
