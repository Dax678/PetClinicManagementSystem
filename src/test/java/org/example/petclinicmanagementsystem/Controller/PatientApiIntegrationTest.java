package org.example.petclinicmanagementsystem.Controller;

import com.jayway.jsonpath.JsonPath;
import org.example.petclinicmanagementsystem.Data.Entity.*;
import org.example.petclinicmanagementsystem.Repository.OwnerRepository;
import org.example.petclinicmanagementsystem.Repository.PatientRepository;
import org.example.petclinicmanagementsystem.Repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
@Import(TestcontainersConfiguration.class)
class PatientApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    List<Patient> patients;
    List<Owner> owners;
    List<User> users;

    String ownerJwtToken;
    String veterinarianJwtToken;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("schema.sql");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setUp() throws Exception {
        userRepository.deleteAll();
        patientRepository.deleteAll();
        ownerRepository.deleteAll();

        userRepository.flush();
        patientRepository.flush();
        ownerRepository.flush();

        patients = new ArrayList<>();
        owners = new ArrayList<>();
        users = new ArrayList<>();

        users.add(new User("owner1", encoder.encode("password1"), "owner1@example.com", UserRole.OWNER.getRoleWithPrefix()));
        users.add(new User("veterinarian2", encoder.encode("password2"), "veterinarian1@example.com", UserRole.VETERINARIAN.getRoleWithPrefix()));

        owners.add(new Owner(users.getFirst().getId(), "John", "Doe", "555-123-456", "123 Maple Street", "Springfield", "IL", "62704"));
        owners.add(new Owner(users.get(1).getId(), "Jane", "Smith", "555-987-654", "456 Oak Avenue", "Los Angeles", "CA", "90001"));

        patients.add(new Patient(owners.getFirst().getId(), "Bella", LocalDate.of(2018, 5, 12), EAnimalGender.FEMALE, 10.5f, "Labrador", null));
        patients.add(new Patient(owners.getFirst().getId(), "Buddy", LocalDate.of(2018, 5, 10), EAnimalGender.MALE, 30.5f, "Golden Retriever", null));
        patients.add(new Patient(owners.get(1).getId(), "Mittens", LocalDate.of(2020, 7, 21), EAnimalGender.FEMALE, 4.2f, "Siamese Cat", null));

        userRepository.saveAll(users);
        ownerRepository.saveAll(owners);
        patientRepository.saveAll(patients);

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"owner1\", \"password\": \"password1\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ownerJwtToken = JsonPath.parse(response).read("$.token");

        response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"veterinarian2\", \"password\": \"password2\"}"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        veterinarianJwtToken = JsonPath.parse(response).read("$.token");
    }

    @Test
    @DisplayName("GET /api/patients should return list of patients")
    void getAllPatients_ReturnsListOfPatients() throws Exception {
        mockMvc.perform(get("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + veterinarianJwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patient").isArray())
                .andExpect(jsonPath("$.patient[0].id").value(patients.getFirst().getId()))
                .andExpect(jsonPath("$.patient[0].ownerId").value(patients.getFirst().getOwnerId()))
                .andExpect(jsonPath("$.patient[0].name").value(patients.getFirst().getName()))
                .andExpect(jsonPath("$.patient[0].dateOfBirth").value(patients.getFirst().getDateOfBirth().toString()))
                .andExpect(jsonPath("$.patient[0].gender").value(patients.getFirst().getGender().getAnimalGender()))
                .andExpect(jsonPath("$.patient[0].weight").value(patients.getFirst().getWeight()))
                .andExpect(jsonPath("$.patient[0].breed").value(patients.getFirst().getBreed()));
    }

    @Test
    @DisplayName("GET /api/patients/id/{id} should return patient from real DB")
    void getPatientById_ReturnsPatient() throws Exception {
        Long patientId = patients.get(1).getId();

        mockMvc.perform(get("/api/patients/id/{0}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerJwtToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patient.id").value(patients.get(1).getId()))
                .andExpect(jsonPath("$.patient.ownerId").value(patients.get(1).getOwnerId()))
                .andExpect(jsonPath("$.patient.name").value(patients.get(1).getName()))
                .andExpect(jsonPath("$.patient.dateOfBirth").value(patients.get(1).getDateOfBirth().toString()))
                .andExpect(jsonPath("$.patient.gender").value(patients.get(1).getGender().getAnimalGender()))
                .andExpect(jsonPath("$.patient.weight").value(patients.get(1).getWeight()))
                .andExpect(jsonPath("$.patient.breed").value(patients.get(1).getBreed()));
    }

    @Test
    @DisplayName("GET /api/patients/id/{id} should return 404 when patient is not found")
    void getPatientById_shouldReturn404() throws Exception {
        Long patientId = 999L;

        mockMvc.perform(get("/api/patients/id/{0}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + ownerJwtToken))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Patient with id: 999 not found"));
    }

    @Test
    @DisplayName("GET /api/patients/id/{id} should return 400 when patient id is invalid")
    void getPatientById_shouldReturn400() throws Exception {
        Long patientId = 0L;

        mockMvc.perform(get("/api/patients/id/{0}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + ownerJwtToken))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("getPatientById.id: Id must be a positive number"));
    }
}