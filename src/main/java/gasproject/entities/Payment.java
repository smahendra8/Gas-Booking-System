package gasproject.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private  Long id;

   private String customerId;

   private Double amount;


   @Enumerated(EnumType.STRING)


   private PaymentMode payment;

   @Enumerated(EnumType.STRING)

 private PaymentStatus status= PaymentStatus.SUCCESS;

   private LocalDateTime paidDate= LocalDateTime.now();

   public enum PaymentMode{
       CASH,UPI,CARD,NETBANKING
   }

   public enum PaymentStatus{
       PENDING,SUCCESS,FAILED
   }
}
