package gasproject.controllers;

import gasproject.cofig.QRCodeService;
import gasproject.cofig.UpiConfig;
import gasproject.entities.Payment;
import gasproject.repositories.PaymentRepository;
import gasproject.service.PaymentService;

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
public class PaymentController {

    private final PaymentService paymentService;
    private final QRCodeService qrCodeService;
    private final UpiConfig upiConfig;
    private final PaymentRepository paymentRepository;

    @PostMapping
    public ResponseEntity<?> createPayment(
            @RequestParam String customerId,
            @RequestParam Double amount,
            @RequestParam Payment.PaymentMode mode) {
        Payment payment = paymentService.createPendingPayment(customerId, amount, mode);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getpaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    private String upiId;

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

     @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments(){
        List<Payment>payments=paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }


    @PatchMapping("{id}/confirm")
    public ResponseEntity<?> confirmPayment(@PathVariable Long id){
        Payment payment = paymentService.confirmPayment(id);
        return ResponseEntity.ok(payment);
    }


}






