package gasproject.repositories;

import gasproject.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity,Long> {


    //today//
    List<BookingEntity>findByCustomerId(Long customerId);

    // Customer ID తో latest booking తీసుకో
    BookingEntity findTopByCustomerIdOrderByBookingDateDesc(String customerId);

}
