package gasproject.controllers;

import gasproject.entities.BookingEntity;
import gasproject.entities.CylinderEntity;
import gasproject.service.BookingService;
import gasproject.service.CylinderService;
import gasproject.service.Userservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
@Tag(name = "\uD83D\uDCCB Booking_Section",description = "\uD83D\uDCDD [Details of Bookings] ")
public class BookingController {

    private final BookingService bookingService;



    @Operation(summary = "[Add_Booking]",description = "Booking a Cylinder")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking  Sucessfully Created",
                    content = @Content(schema = @Schema(implementation = BookingEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid  Booking Details",
                    content =@Content
            )
    })



    @PostMapping
    public ResponseEntity<?> createBooking(
            @RequestParam String customerId ,

            @RequestParam String deliveryAddress,
             @RequestParam CylinderEntity.Type type
    ) {
        String booking = bookingService.createBooking(customerId, deliveryAddress,type );
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }


    @Operation(summary = "[Booking_By_ID]",description = "Get Booking by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking  Sucessfully Found",
                    content = @Content(schema = @Schema(implementation = BookingEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid  Booking ID",
                    content =@Content
            )
    })


    @GetMapping("/{id}")
    public ResponseEntity<BookingEntity> getBookingById(@PathVariable Long id) {
        BookingEntity booking = bookingService.getBookingId(id);
        return ResponseEntity.ok(booking);
    }


    @Operation(summary = "[All_Bookings]",description = "Getting All Bookings")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "All Bookings Sucessfully Displayed",
                    content = @Content(schema = @Schema(implementation = BookingEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No  Bookings Found",
                    content =@Content
            )
    })



    @GetMapping
    public ResponseEntity<List<BookingEntity>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }



    @Operation(summary = "[Booking_History]",description = "Getting Booking History")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking History Sucessfully Dispalyed",
                    content = @Content(schema = @Schema(implementation = BookingEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Customer ID",
                    content =@Content
            )
    })




    //today
    @GetMapping("/history/{customerId}")
    public ResponseEntity<List<BookingEntity>>getBookingHistory(@PathVariable String customerId) {
        List<BookingEntity>bookings =bookingService.BookingHistoryByCustomerId(customerId);
    return ResponseEntity.ok(bookings);
    }


    @Operation(summary = "[Cancel_Booking]",description = "Cancelling Booking by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Booking  Sucessfully Cancelled",
                    content = @Content(schema = @Schema(implementation = BookingEntity.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid  Booking Id",
                    content =@Content
            )
    })


    //Today
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<BookingEntity>cancelBooking(@PathVariable Long id) {
        BookingEntity booking = bookingService.cancelBooking(id);
        return ResponseEntity.ok(booking);

    }


}

