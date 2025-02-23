package org.example.petclinicmanagementsystem.Controller;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.example.petclinicmanagementsystem.Data.DTO.PatientDTO;
import org.example.petclinicmanagementsystem.Service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping(value = "/api/patient")
public class PatientController {
    private final PatientService patientService;

    /**
     * Retrieves a list of all patients.
     *
     * @return ResponseEntity containing a map with the key "patient" and a list of {@link PatientDTO} objects,
     *         along with an HTTP status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<Map<String, List<PatientDTO>>> getAllPatients() {
        List<PatientDTO> patients = patientService.getAllPatients();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("patient", patients));
    }

    /**
     * Retrieves a patient by their ID.
     *
     * @param id The ID of the patient, which must be a positive number.
     * @return ResponseEntity containing a map with the key "patient" and the corresponding {@link PatientDTO} object,
     *         along with an HTTP status 200 (OK).
     * @throws ElementNotFoundException if no patient is found with the given ID.
     */
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<Map<String, PatientDTO>> getPatientById(@PathVariable @Positive(message = "Id must be a positive number") int id) {
        PatientDTO patient = patientService.getPatientById(id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("patient", patient));
    }
}
