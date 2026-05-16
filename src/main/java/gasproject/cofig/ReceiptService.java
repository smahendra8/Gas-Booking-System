package gasproject.cofig;

import com.itextpdf.html2pdf.HtmlConverter;
import gasproject.DTO.ReceiptResponse;
import gasproject.entities.Payment;
import gasproject.repositories.BookingRepository;
import gasproject.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    private  final BookingRepository bookingRepository;
    private  final PaymentRepository paymentRepository;

    public byte[] generateReceiptPdf(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->new RuntimeException("Payment not found"));

        String html = buildReceiptHtml(payment);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, outputStream);
        return outputStream.toByteArray();
    }

    public ReceiptResponse generateReceiptJson(Long paymentId){
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()->new RuntimeException("Payment not found"));

        return  ReceiptResponse.builder()
                .receiptNo("RCP-"+paymentId)
                .customerId(payment.getCustomerId())
                .amount(payment.getAmount())
                .paymentMode(String.valueOf(payment.getPayment()))
                .status(String.valueOf(payment.getStatus()))
                .dateTime(payment.getPaidDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:a")))
                .build();
    }


    private String buildReceiptHtml(Payment payment){
        return "<html><body style='font-family:Arial;padding:20px'>" +
                "<h2 style='text-align:center;color:#2e7d32'>GAS BOOKING RECEIPT</h2>" +
                "<hr/>" +
                "<table width='100%'>" +
                "<tr><td><b>Receipt No</b></td><td>RCP-" + payment.getId() + "</td></tr>" +
                "<tr><td><b>Date & Time</b></td><td>" +
                payment.getPaidDate().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a")) +
                "</td></tr>" +
                "<tr><td><b>Customer ID</b></td><td>" + payment.getCustomerId() + "</td></tr>" +
                "<tr><td><b>Amount</b></td><td>₹" + payment.getAmount() + "</td></tr>" +
                "<tr><td><b>Payment Mode</b></td><td>" + payment.getPayment() + "</td></tr>" +
                "<tr><td><b>Status</b></td><td style='color:green'>" + payment.getStatus() + " ✅</td></tr>" +
                "</table>" +
                "<hr/>" +
                "<h3 style='text-align:center'>Thank You! 🙏</h3>" +
                "</body></html>";
    }



}
