package gasproject.entities;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

import java.time.LocalDate;

@Entity
@Table(name="bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    //today


     @JoinColumn(name = "cylinder_id")

    private String cylinderType;



    private String customerId;

    private LocalDate bookingDate=LocalDate.now();

    private String deliveryAddress;



    @Enumerated(EnumType.STRING)
    private Status status=Status.PENDING;

    public enum Status {
        PENDING,CONFIRMED,CANCELLED,DELIVERED;
    }



}
