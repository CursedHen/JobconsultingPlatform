package com.example.springboot.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean booked;

    private AvailabilityStatus status;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;

    public void setConsultant(Consultant mentor){
        this.consultant = mentor;
    }


    public void setStatus(AvailabilityStatus availabilityStatus) {
        if (this.status == AvailabilityStatus.BOOKED
                && availabilityStatus == AvailabilityStatus.AVAILABLE) {
            throw new IllegalStateException(
                    "Cannot revert a booked slot to available"
            );
        }
        if(this.status == AvailabilityStatus.CANCELLED
            && availabilityStatus == AvailabilityStatus.BOOKED){
            throw new IllegalStateException(
                    "Cannot book a cancelled slot"
            );
        }
        this.status = availabilityStatus;
    }

    public void setStartTime(LocalDateTime startTime) {
        if (this.endTime != null && startTime.isAfter(this.endTime)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (this.startTime != null && endTime.isBefore(this.startTime)) {
            throw new IllegalArgumentException("End time cannot be before start time");
        }
        this.endTime = endTime;
    }
}
