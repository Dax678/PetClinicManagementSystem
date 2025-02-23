package org.example.petclinicmanagementsystem.Controller;

import org.example.petclinicmanagementsystem.Configuration.Exception.ElementNotFoundException;
import org.example.petclinicmanagementsystem.Data.DTO.PatientDTO;
import org.example.petclinicmanagementsystem.Data.Entity.EAnimalGender;
import org.example.petclinicmanagementsystem.Service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PatientController.class)
class PatientControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    private PatientDTO samplePatient;

    @BeforeEach
    void setUp() {
        samplePatient = new PatientDTO(1L, 1L, "Bella", LocalDate.of(2018, 5, 12), EAnimalGender.FEMALE, 10.5f, "Labrador");
    }

    @Test
    @DisplayName("GET /api/patient should return list of patients")
    void getAllPatients_ReturnsListOfPatients() throws Exception {
        List<PatientDTO> patientList = List.of(samplePatient);

        when(patientService.getAllPatients()).thenReturn(patientList);

        mockMvc.perform(get("/api/patient")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patient").isArray())
                .andExpect(jsonPath("$.patient[0].name").value("Bella"));
    }

    @Test
    @DisplayName("GET /api/patient/id/{id} should return patient with id")
    void getPatientById_ReturnsPatient() throws Exception {
        when(patientService.getPatientById(1)).thenReturn(samplePatient);

        mockMvc.perform(get("/api/patient/id/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patient.name").value("Bella"))
                .andExpect(jsonPath("$.patient.breed").value("Labrador"));
    }
}