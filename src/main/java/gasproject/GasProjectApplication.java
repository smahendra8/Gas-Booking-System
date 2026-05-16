package gasproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class GasProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GasProjectApplication.class, args);
    }

}
