//<<<<<<< HEAD
//package com.example.springboot.model;
//
//
//import com.example.springboot.model.Availability;
//import com.example.springboot.model.RegistrationStatus;
//import jakarta.persistence.*;
//import lombok.Data.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Consultant {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;
//
//    private String firstName;
//    private String lastName;
//
//    private String email;
//
//    private String bio;
//    private Double hourlyRate;
//
//    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
//    private List<Availability> availabilities = new ArrayList<>();
//
//    public static void addAvailability(Availability availability) {
//        availability.setConsultant(this);
//
//        this.availabilities.add(availability);
//    }
//
//
//
//    public void removeAvailability(Availability availability) {
//        this.availabilities.remove(availability);
//        availability.setConsultant(null);
//    }
//}


package com.example.springboot.model;

import com.example.springboot.model.RegistrationStatus;
import com.example.springboot.model.Availability;
import jakarta.persistence.*;
import lombok.Data.*;


import java.util.ArrayList;
import java.util.List;
/**
 * Represents a Consultant on the platform.
 * Admins approve or reject consultants via UC11.
 */
@Entity
public class Consultant {
    private Long id;
    private String name;
    private String email;
    private String specialization; // e.g. "Software Consulting", "Career Advising"
    private RegistrationStatus status; // PENDING, APPROVED, REJECTED
    private Double hourlyRate;

    public Consultant(Long id, String name, String email, String specialization, Double hourlyRate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.status = RegistrationStatus.PENDING; // all new consultants start as PENDING
        this.hourlyRate = hourlyRate;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSpecialization() { return specialization; }
    public RegistrationStatus getStatus() { return status; }
    public Double getHourlyRate(){ return hourlyRate; }

    public void setStatus(RegistrationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Consultant{id=" + id + ", name='" + name + "', email='" + email +
               "', specialization='" + specialization + "', status=" + status + "}";
    }

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Availability> availabilities = new ArrayList<>();

    public void addAvailability(Availability availability) {
        availability.setConsultant(this);

        this.availabilities.add(availability);
    }


    public void removeAvailability(Availability availability) {
        this.availabilities.remove(availability);
        availability.setConsultant(null);
    }
}

