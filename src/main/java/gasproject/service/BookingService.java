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

    public String createBooking(String customerId, String deliveryAddress, CylinderEntity.Type type) {
        UserEntity userEntity = userRepository.findByCustomerId(customerId);


        if (userEntity == null) {
            throw new RuntimeException("Customer Id is null");
        }
        List<CylinderEntity> cylinders = cylinderRepository.findByType(type);

        CylinderEntity cylinder = cylinders.stream()
                .filter(c -> c.getStatus() == CylinderEntity.Status.AVAILABLE)
                .findFirst().orElseThrow(() -> new RuntimeException("Cylinder is not available"));
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
    public List<BookingEntity> BookingHistoryByCustomerId(String customerId) {
        UserEntity user = userRepository.findByCustomerId(customerId);
        if (user == null) {
            throw new RuntimeException("Invalid Customer Id");
        }
        return bookingRepository.findByCustomerId(user.getCustomerId());
    }

    //Today
    public BookingEntity cancelBooking(Long id) {
        BookingEntity existingBooking = getBookingId(id);
        if (existingBooking.getStatus() == BookingEntity.Status.DELIVERED) {
            throw new RuntimeException("Delivered Booking Can't Be Cancelled");
        }
        List<CylinderEntity> cylinderEntity = cylinderRepository.findByType(CylinderEntity.Type.valueOf(existingBooking.getCylinderType()));

        CylinderEntity cylinder = cylinderEntity.stream()
                .filter(c -> c.getStatus() == CylinderEntity.Status.AVAILABLE)
                .findFirst()
                .orElse(null);


        if (cylinder == null) {
            cylinder.setStatus(CylinderEntity.Status.CANCELLED);
            cylinderRepository.save(cylinder);
        }

            existingBooking.setStatus(BookingEntity.Status.CANCELLED);
            // bookingRepository.save(existingBooking);
            return bookingRepository.save(existingBooking);
        }


    }
