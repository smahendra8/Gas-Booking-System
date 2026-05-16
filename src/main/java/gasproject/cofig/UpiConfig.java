package gasproject.cofig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.upi")
@Data
public class UpiConfig {

    private  String id;
    private  String customerId;
}
