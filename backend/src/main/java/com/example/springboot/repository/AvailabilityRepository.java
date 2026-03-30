package com.example.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.model.Availability;
import com.example.springboot.model.AvailabilityStatus;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    
    List<Availability> findByConsultant_Id(Long consultantId);

    List<Availability> findByStatus(AvailabilityStatus status);
}
