package gasproject.controllers;

import gasproject.entities.BookingEntity;
import gasproject.entities.CylinderEntity;
import gasproject.service.BookingService;
import gasproject.service.CylinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;


    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestParam String customerId ,

            @RequestParam String deliveryAddress,
             @RequestParam CylinderEntity.Type type
    ) {
        String booking = bookingService.createBooking(customerId, deliveryAddress,type );
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingEntity> getBookingById(@PathVariable Long id) {
        BookingEntity booking = bookingService.getBookingId(id);
        return ResponseEntity.ok(booking);
    }



    @GetMapping
    public ResponseEntity<List<BookingEntity>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }


    //today
    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<BookingEntity>>getBookingHistory(@PathVariable String customerId) {
        List<BookingEntity>bookings =bookingService.BookingHistoryByCustomerId(customerId);
    return ResponseEntity.ok(bookings);
    }

    //Today
    @PatchMapping("/{id}cancel")
    public ResponseEntity<BookingEntity>cancelBooking(@PathVariable Long id) {
        BookingEntity booking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(booking);

    }








}

