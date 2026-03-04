package com.example.springboot.model;

import java.time.LocalDateTime;

public class AvailabilityFactory {

    public static Availability create(LocalDateTime start,
                                      LocalDateTime end) {

        Availability availability = new Availability();
        availability.setStartTime(start);
        availability.setEndTime(end);
        availability.setStatus(AvailabilityStatus.AVAILABLE);

        return availability;
    }
}
