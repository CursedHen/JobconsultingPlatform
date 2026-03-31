package com.example.springboot.dto;

import java.time.LocalDateTime;

public class AvailabilityRequest {

    private Long consultantId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Default constructor
    public AvailabilityRequest() {}

    public AvailabilityRequest(Long consultantId, LocalDateTime startTime, LocalDateTime endTime) {
        this.consultantId = consultantId;
        this.startTime = startTime;
        this.endTime = endTime;
    }


    // Getters
    public Long getConsultantId() {
        return consultantId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    // Setters
    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}