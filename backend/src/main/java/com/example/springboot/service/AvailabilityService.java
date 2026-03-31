package com.example.springboot.service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.springboot.model.Availability;

public interface AvailabilityService {
     List<Availability> createAvailability(Long consultantId,
                            LocalDateTime start,
                            LocalDateTime end);

    List<Availability> getAllAvailability();                   
}
