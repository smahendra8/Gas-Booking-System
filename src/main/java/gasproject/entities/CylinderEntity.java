package gasproject.entities;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cylinders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CylinderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Enumerated(EnumType.STRING)

    private Type type=Type.COMMERCIAL;//Domestic, Commericial

    private Double price;




    public enum Type{
        DOMESTIC,COMMERCIAL
    }

    @Enumerated(EnumType.STRING)


    private Status status =Status.AVAILABLE;

    public enum Status{
        AVAILABLE,CONFIRMED,DELIVERED,PENDING,CANCELLED;
    }

}
