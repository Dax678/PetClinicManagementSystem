package org.example.petclinicmanagementsystem.Controller;

import com.jayway.jsonpath.JsonPath;
import org.example.petclinicmanagementsystem.Data.Entity.*;
import org.example.petclinicmanagementsystem.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

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
class AuthApiIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder encoder;

    List<User> users;

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
    void setUp() {
        userRepository.deleteAll();

        userRepository.flush();

        users = new ArrayList<>();

        users.add(new User("owner1", encoder.encode("password1"), "owner@example.com", UserRole.OWNER.getRoleWithPrefix()));
        users.add(new User("veterinarian2", encoder.encode("password2"), "veterinarian@example.com", UserRole.VETERINARIAN.getRoleWithPrefix()));
        users.add(new User("admin3", encoder.encode("password3"), "admin@example.com", UserRole.ADMIN.getRoleWithPrefix()));

        userRepository.saveAll(users);
    }

    @Test
    void registerUserAndLogin() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        String email = "testuser@example.com";

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"email\": \"" + email + "\"}"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void registerUserWithExistingUsername_shouldReturn400() throws Exception {
        String username = users.getFirst().getUsername();
        String password = "password1";
        String email = "email@example.com";

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"email\": \"" + email + "\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with username: " + username + " already exists"));
    }

    @Test
    void registerUserWithExistingEmail_shouldReturn400() throws Exception {
        String username = "owner2";
        String password = "password1";
        String email = users.getFirst().getEmail();

        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\", \"email\": \"" + email + "\"}"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("User with email: " + email + " already exists"));
    }

    @Test
    void testPublicAccessToEndpoint() throws Exception {
        mockMvc.perform(get("/api/test/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testPublicHasNoAccessToOwnerEndpoint() throws Exception {
        mockMvc.perform(get("/api/test/owner"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testOwnerHasAccessToEndpoint() throws Exception {
        String username = users.getFirst().getUsername();
        String password = "password1";

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String jwtToken = JsonPath.parse(response).read("$.token");

        mockMvc.perform(get("/api/test/owner")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testOwnerHasNoAccessToVeterinarianEndpoint() throws Exception {
        String username = users.getFirst().getUsername();
        String password = "password1";

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String jwtToken = JsonPath.parse(response).read("$.token");

        mockMvc.perform(get("/api/test/veterinarian")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void testVeterinarianHasAccessToEndpoint() throws Exception {
        String username = users.get(1).getUsername();
        String password = "password2";

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String jwtToken = JsonPath.parse(response).read("$.token");

        mockMvc.perform(get("/api/test/veterinarian")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testVeterinarianHasNoAccessToAdminEndpoint() throws Exception {
        String username = users.get(1).getUsername();
        String password = "password2";

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String jwtToken = JsonPath.parse(response).read("$.token");

        mockMvc.perform(get("/api/test/admin")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void testAdminHasAccessToEndpoint() throws Exception {
        String username = users.get(2).getUsername();
        String password = "password3";

        String response = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String jwtToken = JsonPath.parse(response).read("$.token");

        mockMvc.perform(get("/api/test/admin")
                        .header("Authorization", "Bearer " + jwtToken))
                .andDo(print())
                .andExpect(status().isOk());
    }
}