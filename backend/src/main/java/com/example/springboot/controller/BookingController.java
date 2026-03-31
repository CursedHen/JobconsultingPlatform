package com.example.springboot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.model.Booking;
import com.example.springboot.model.BookingRequestDTO;
import com.example.springboot.model.CancellationResult;
import com.example.springboot.service.ClientBookingService;

@RestController
@RequestMapping("/bookings")
@CrossOrigin // IMPORTANT for React
public class BookingController {

    private final ClientBookingService bookingService;

    public BookingController(ClientBookingService bookingService) {
        this.bookingService = bookingService;
    }

    // CREATE booking (like your availability POST)
    @PostMapping
    public Booking createBooking(@RequestBody BookingRequestDTO request) {
        return bookingService.requestBooking(request);
    }

    // GET all bookings for a client
    @GetMapping("/client/{clientId}")
    public List<Booking> getBookings(@PathVariable Long clientId) {
        return bookingService.getBookingsForClient(clientId);
    }

    // CANCEL booking
    @DeleteMapping("/{bookingId}")
    public CancellationResult cancelBooking(
            @PathVariable Long bookingId,
            @RequestParam Long clientId) {
        return bookingService.cancelBooking(bookingId, clientId);
    }
}