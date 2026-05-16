package gasproject.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceiptResponse {
    private String receiptNo;
    private String customerId;
    private Double amount;
    private String paymentMode;
    private  String status;
    private  String dateTime;

}
