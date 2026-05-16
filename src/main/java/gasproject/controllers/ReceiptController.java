package gasproject.controllers;

import gasproject.DTO.ReceiptResponse;
import gasproject.cofig.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
public class ReceiptController {
    private final ReceiptService receiptService;

    @GetMapping(value = "/{paymentId}/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]>getReceiptPdf(@PathVariable Long paymentId){
        byte[]  pdf = receiptService.generateReceiptPdf(paymentId);
        return ResponseEntity.ok()
                .header("Content-Disposition","attachment; filename=receipt-"+paymentId+".pdf")
                .header("Content-Type","application/pdf")
                .body(pdf);
    }


    @GetMapping("/{paymentId}/json")
    public  ResponseEntity<ReceiptResponse>getReceiptJson(@PathVariable Long paymentId){
        ReceiptResponse receiptResponse = receiptService.generateReceiptJson(paymentId);
        return ResponseEntity.ok().body(receiptResponse);
    }



}
