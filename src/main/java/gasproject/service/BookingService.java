package gasproject.service;

import gasproject.entities.BookingEntity;
import gasproject.entities.CylinderEntity;
import gasproject.entities.UserEntity;
import gasproject.repositories.BookingRepository;
import gasproject.repositories.CylinderRepository;
import gasproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final Userservice userservice;
    private final CylinderRepository cylinderRepository;
    private final UserRepository userRepository;

    public String createBooking(String customerId, String deliveryAddress,CylinderEntity.Type type) {
        UserEntity userEntity =userRepository.findByCustomerId(customerId);

        CylinderEntity cylinder = cylinderRepository.findByType(type);

        if (userEntity==null) {
            throw new RuntimeException("Customer Id is null");
        }
        BookingEntity booking = BookingEntity.builder()
                .customerId(userEntity.getCustomerId())
                .deliveryAddress(deliveryAddress)
                .status(BookingEntity.Status.PENDING)
                .cylinderType(cylinder.getType().name())


                .bookingDate(LocalDate.now()).build();

                bookingRepository.save(booking);
                return "Booking Created Successfully";

    }


    public BookingEntity getBookingId(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public List<BookingEntity> getAllBookings() {
        return bookingRepository.findAll();
    }




    //yesterday
    public List<BookingEntity>BookingHistoryByCustomerId(String customerId) {
        UserEntity user = userRepository.findByCustomerId(customerId);
        if (user==null) {
            throw new RuntimeException("Invalid Customer I");
        }
        return bookingRepository.findByCustomerId(user.getId());
    }

    //Today
    public  BookingEntity cancelBooking(Long id){
        BookingEntity existingBooking = getBookingId(id);
        if(existingBooking.getStatus()==BookingEntity.Status.DELIVERED){
            throw new RuntimeException("Delivered Booking Can't Be Cancelled");
        }
      CylinderEntity  cylinderEntity = cylinderRepository.findByType(CylinderEntity.Type.valueOf(String.valueOf(existingBooking.getCylinderType())));
       cylinderEntity.setStatus(CylinderEntity.Status.CANCELLED);
       cylinderRepository.save(cylinderEntity);


        existingBooking.setStatus(BookingEntity.Status.CANCELLED);
       // bookingRepository.save(existingBooking);
        return bookingRepository.save(existingBooking);
    }








}