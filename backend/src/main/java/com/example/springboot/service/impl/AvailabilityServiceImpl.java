package com.example.springboot.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springboot.model.Availability;
import com.example.springboot.model.AvailabilityFactory;
import com.example.springboot.model.Consultant;
import com.example.springboot.repository.AvailabilityRepository;
import com.example.springboot.repository.ConsultantRepository;
import com.example.springboot.service.AvailabilityService;
@Service
public class AvailabilityServiceImpl implements AvailabilityService {
    private final ConsultantRepository consultantRepository;
    private final AvailabilityFactory availabilityFactory;
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityServiceImpl(ConsultantRepository consultantRepository,
                                   AvailabilityFactory availabilityFactory,
                                   AvailabilityRepository availabilityRepository
    ){
        this.consultantRepository = consultantRepository;
        this.availabilityFactory = availabilityFactory;
        this.availabilityRepository = availabilityRepository;
    }

   @Override
   @Transactional
    public List<Availability> createAvailability(Long consultantId, LocalDateTime start, LocalDateTime end) {
    // 1. Find the consultant (Consultant)
    Consultant consultant = consultantRepository.findById(consultantId)
            .orElseThrow(() -> new RuntimeException("Consultant not found"));

    // 2. Use the INJECTED factory (lowercase 'a')
    Availability availability = availabilityFactory.create(start, end);
    // 3. Call the method on the OBJECT 
    consultant.addAvailability(availability);

    consultantRepository.save(consultant);
    return consultant.getAvailabilities();
}

    @Override
    public List<Availability> getAllAvailability() {
        return availabilityRepository.findAll();
    }
}
