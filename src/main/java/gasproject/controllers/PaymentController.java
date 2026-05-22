package gasproject.controllers;

import gasproject.cofig.QRCodeService;
import gasproject.cofig.UpiConfig;
import gasproject.entities.Payment;
import gasproject.repositories.PaymentRepository;
import gasproject.service.PaymentService;

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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
@Tag(name = "\uD83D\uDCB3 Payments_Section",description = " \uD83D\uDCB0 [Details of Payments] ")
public class PaymentController {

    private final PaymentService paymentService;
    private final QRCodeService qrCodeService;
    private final UpiConfig upiConfig;
    private final PaymentRepository paymentRepository;



    @Operation(summary = "[Payments_By_Cash]",description = " Payments Accepting  By Cash Only")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment Sucessfully  Done By Cash Way ",
                    content = @Content(schema = @Schema(implementation = Payment.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Payment Details so don't pay money",
                    content =@Content
            )
    })


    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestParam String customerId,
            @RequestParam Double amount,
            @RequestParam Payment.PaymentMode mode) {
        Payment payment = paymentService.createPendingPayment(customerId, amount, mode);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);

    }




    @Operation(summary = "[Payments_Status]",description = "Getting Payment Status by Id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = " Sucessfully Showed Payment Status",
                    content = @Content(schema = @Schema(implementation = Payment.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Payment Id  to View Payment Status",
                    content =@Content
            )
    })

    @GetMapping("/{id}/status")
    public ResponseEntity<Payment> getpaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    private String upiId;





    @Operation(summary = "[Payments_By_QR]",description = "Make Payments  By QR Code Scanning")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "QR Code Generated Sucessfully",
                    content = @Content(schema = @Schema(implementation = Payment.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "QR Code Generation  Failed ",
                    content =@Content
            )
    })

    @PostMapping(value = "/qr", produces = "image/png")
    public ResponseEntity<byte[]> getPaymentQRCode(@RequestParam Double amount,@RequestParam String customerId){
        try {
            byte[] qrCode = qrCodeService.generateUPIQRCodeBytes(upiConfig.getId(),upiConfig.getCustomerId(),amount);

            Payment payment= paymentService.createPendingPayment(customerId, amount, Payment.PaymentMode.UPI);
            System.out.println("✅ QR Code Generated!");
            System.out.println("Payment ID: " + payment.getId());
            System.out.println("Status:PENDING");

            return ResponseEntity.ok()
                    .header("Content-Type","image/png")
                    .header("Payment-Id",String.valueOf(payment.getId()))
                    .body(qrCode);







            //payment.setCustomerId(customerId);
            //payment.setAmount(amount);
            //payment.setPayment(Payment.PaymentMode.UPI);

           // payment.setStatus(Payment.PaymentStatus.PENDING);
            //payment.setPaidDate(LocalDateTime.now());

            //paymentRepository.save(payment);


        }catch (Exception e){
            throw new RuntimeException("QR Code generation failed");

        }
    }




    @Operation(summary = "[Payments_History]",description = "Getting Payements History ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payments History Sucessfully Showed",
                    content = @Content(schema = @Schema(implementation = Payment.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "No Results Found ",
                    content =@Content
            )
    })

     @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment>payments=paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }



    @Operation(summary = "[Confirm_Payment]",description = "Confirming Payment  by Id ")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment Confirmed Sucessfully ",
                    content = @Content(schema = @Schema(implementation = Payment.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Payment Id Details ",
                    content =@Content
            )
    })


    @PatchMapping("/{id}/confirm")
    public ResponseEntity<?> confirmPayment(@PathVariable Long id){
        Payment payment = paymentService.confirmPayment(id);
        return ResponseEntity.ok(payment);
    }


}






