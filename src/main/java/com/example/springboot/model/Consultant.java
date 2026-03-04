package com.example.springboot.model;


import jakarta.persistence.*;
import lombok.Data.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Consultant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;

    private String email;

    private String bio;
    private Double hourlyRate;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    private List<Availability> availabilities = new ArrayList<>();

    public static void addAvailability(Availability availability) {
        availability.setConsultant(this);

        this.availabilities.add(availability);
    }



    public void removeAvailability(Availability availability) {
        this.availabilities.remove(availability);
        availability.setConsultant(null);
    }
}


