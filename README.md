# Pet Clinic Management System - CV Project
**This is a project created for CV purposes.**

## 📌 About the Project
Pet Clinic Management System is a web-based application designed to help veterinary clinics manage their patients and appointments efficiently. 
The system allows veterinarians to log in, manage pet medical records, and schedule appointments while providing owners with easy access to their pet's health information.

## 🏗️ Tech Stack
- **Backend**: Java, Spring Boot, Spring Security, Spring Data JPA, Hibernate
- **Database**: PostgreSQL
- **Authentication**: JWT (Json Web Token)
- **Testing**: JUnit, Testcontainers, MockMvc
- **Caching**: Ehcache
- **API Documentation**: Springdoc OpenAPI (Swagger UI 3)

## 🔧 Installation & Setup  
### Prerequisites  
Ensure you have the following installed:  
- Java 17+  
- Docker (for Testcontainers)  
- PostgreSQL

## 📖 API Endpoints
### Patients
- **GET /api/patient** - Get all patients
- **GET /api/patient/id/{id}** - Get a patient by ID
