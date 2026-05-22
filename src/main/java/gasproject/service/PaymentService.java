package gasproject.service;

import gasproject.entities.BookingEntity;
import gasproject.entities.Payment;
import gasproject.entities.UserEntity;
import gasproject.repositories.BookingRepository;
import gasproject.repositories.PaymentRepository;
import gasproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public Payment createPendingPayment(String customerId, Double amount, Payment.PaymentMode mode){
        UserEntity user= userRepository.findByCustomerId(customerId);

        if (user==null){
            throw new RuntimeException("Customer Id not Found");
        }
        Payment payment=Payment.builder()
                .customerId(customerId)
                .amount(amount)
                .payment(mode)
                .status(Payment.PaymentStatus.PENDING)
                .paidDate(LocalDateTime.now())
                .build();
        return paymentRepository.save(payment);

        //Payment savedPayment=paymentRepository.save(payment);
        //System.out.println("✅ Payment Success!");
        //System.out.println("CustomerId:"+ savedPayment.getCustomerId());
        //System.out.println("Amount: ₹" + savedPayment.getAmount());
       // System.out.println("Mode:"+savedPayment.getPayment());
        //System.out.println("Status:"+savedPayment.getStatus());
        // return savedPayment;
    }

    public Payment getPaymentById(Long id){
        return paymentRepository.findById(id).orElseThrow(()->new RuntimeException("Payment not found"));
    }


    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }


    public  Payment updatePaymentStatus(Long id, Payment.PaymentStatus status){
        Payment payment =getPaymentById(id);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }



    /*public Payment confirmPayment(Long id){
        Payment payment =getPaymentById(id);

        if(payment.getStatus()==Payment.PaymentStatus.SUCCESS){
            throw new RuntimeException("Payment Alraedy  Confirmed");
        }

        payment.setStatus(Payment.PaymentStatus.SUCCESS);
        Payment savedPayment = paymentRepository.save(payment);
        System.out.println("✅ Payment Confirmed!");
        System.out.println("Payment ID: " + savedPayment.getId());
        System.out.println("Customer ID: " + savedPayment.getCustomerId());
        System.out.println("Amount: ₹" + savedPayment.getAmount());
        System.out.println("Status: SUCCESS ✅");

        return savedPayment;
    }*/

      //edhi payment jarigiteney sucess vastundhi ledante failed vastundhi//
     /* public Payment confirmPayment(Long id){
        Payment payment =getPaymentById(id);

        if(payment.getStatus()==Payment.PaymentStatus.SUCCESS){
            System.out.println("Payment Done Sucessfully");
            return payment;
        }else {
            payment.setStatus(Payment.PaymentStatus.PENDING);
            System.out.println("Payment NotDone Failed");
            return paymentRepository.save(payment);
        }
      }*/


    public Payment confirmPayment(Long id) {
        Payment payment = getPaymentById(id);

        if (payment.getStatus() == Payment.PaymentStatus.SUCCESS) {
            throw new RuntimeException("Payment DONE Successfully");
        }

        if (payment.getStatus() == Payment.PaymentStatus.PENDING) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            System.out.println("❌ Payment FAILED!");

            BookingEntity booking = bookingRepository.findTopByCustomerIdOrderByBookingDateDesc(payment.getCustomerId());

            if (booking != null && booking.getStatus() == BookingEntity.Status.PENDING) {
                booking.setStatus(BookingEntity.Status.CONFIRMED);
                bookingRepository.save(booking);
                System.out.println("✅ Booking CONFIRMED!");
            }
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            System.out.println("❌ Payment FAILED!");
        }

        return paymentRepository.save(payment);

    }


}
