package com.group4.th.year.project.smart.campus.Management.Controller;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.group4.th.year.project.smart.campus.Management.Booking;
import com.group4.th.year.project.smart.campus.Management.Repo.BookingRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    private final BookingRepo bookingRepo;

    public BookingController(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Cacheable(value = "bookings", key = "'all'")
    @GetMapping
    public List<Booking> getBookings() {
        return bookingRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingRepo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "bookings", allEntries = true)
    @PostMapping
    public ResponseEntity<Booking> addBooking(@RequestBody Booking booking) {
        booking.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingRepo.save(booking));
    }

    @CacheEvict(value = "bookings", allEntries = true)
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return bookingRepo.findById(id)
                .map(booking -> {
                    booking.setBookId(updatedBooking.getBookId());
                    booking.setBookedBy(updatedBooking.getBookedBy());
                    booking.setStartTime(updatedBooking.getStartTime());
                    booking.setEndTime(updatedBooking.getEndTime());
                    booking.setStatus(updatedBooking.getStatus());
                    return ResponseEntity.ok(bookingRepo.save(booking));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @CacheEvict(value = "bookings", allEntries = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        if (!bookingRepo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookingRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


