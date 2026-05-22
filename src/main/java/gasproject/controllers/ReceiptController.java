package gasproject.controllers;

import gasproject.DTO.ReceiptResponse;
import gasproject.cofig.ReceiptService;
import gasproject.service.PaymentService;
import gasproject.service.Userservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipts")
@RequiredArgsConstructor
@Tag(name = "\uD83E\uDDFE  Receipts_Section",description = "\uD83D\uDCC4 [Download Payments Receipts] ")
public class ReceiptController {
    private final ReceiptService receiptService;



    @Operation(summary = "[Payment_Receipt_By_PDF]",description = "Getting payments receipts by payment Id in PDF format")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Downloaded Payment Receipt Sucessfully ",
                    content = @Content(schema = @Schema(implementation = PaymentService.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Payment Id Details to download Payment Receipt",
                    content =@Content
            )
    })


    @GetMapping(value = "/{paymentId}/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]>getReceiptPdf(@PathVariable Long paymentId){
        byte[]  pdf = receiptService.generateReceiptPdf(paymentId);
        return ResponseEntity.ok()
                .header("Content-Disposition","attachment; filename=receipt-"+paymentId+".pdf")
                .header("Content-Type","application/pdf")
                .body(pdf);
    }



    @Operation(summary = "[Payment_Receipt_By_JSON]",description = "Getting Payements Receipts By JSON format")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Payment Sucessfully Displayed",
                    content = @Content(schema = @Schema(implementation = PaymentService.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid Payment Id Details ",
                    content =@Content
            )
    })

    @GetMapping("/{paymentId}/json")
    public  ResponseEntity<ReceiptResponse>getReceiptJson(@PathVariable Long paymentId){
        ReceiptResponse receiptResponse = receiptService.generateReceiptJson(paymentId);
        return ResponseEntity.ok().body(receiptResponse);
    }



}
