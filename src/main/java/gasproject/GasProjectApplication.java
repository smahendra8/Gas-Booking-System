package gasproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;






@OpenAPIDefinition(
        info = @Info(
                title = "🧯 Gas Project API",
                version = "1.0",
                description = "Gas Cylinder Booking & Payment Management System",
                contact = @Contact(
                        name = "Mahendra",
                        email = "sampathimahendra@gmail.com"
                )
        )
)
@SpringBootApplication
@EnableConfigurationProperties
public class GasProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GasProjectApplication.class, args);
    }

}
