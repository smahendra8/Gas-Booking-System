package gasproject.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.context.annotation.Role;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private  Long id;


    private  String name;
    @Column(unique = true)
    private String customerId;

    private  String email;

    private  String password;

    private String mobileNumber;

    private String address;


    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role=Role.USER;

    public  enum Role {
        USER,
        ADMIN
    }
}
