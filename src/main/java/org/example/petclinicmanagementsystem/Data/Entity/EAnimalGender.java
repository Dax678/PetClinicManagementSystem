package org.example.petclinicmanagementsystem.Data.Entity;

public enum EAnimalGender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String gender;

    EAnimalGender(String gender) {
        this.gender = gender;
    }

    public String getAnimalGender() {
        return gender;
    }
}
