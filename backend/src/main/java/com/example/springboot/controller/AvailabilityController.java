package com.example.springboot.controller;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.dto.AvailabilityRequest;
import com.example.springboot.model.Availability;
import com.example.springboot.service.AvailabilityService;

@RestController
@RequestMapping("/availability")
@CrossOrigin(origins = "http://localhost:5173")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @PostMapping
    public void createAvailability(@RequestBody AvailabilityRequest request) {

        availabilityService.createAvailability(
                request.getConsultantId(),
                request.getStartTime(),
                request.getEndTime()
        );
    }

    @GetMapping
    public List<Availability> getAllAvailability() {
        return availabilityService.getAllAvailability();
    }
}