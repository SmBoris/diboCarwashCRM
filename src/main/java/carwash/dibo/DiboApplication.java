package carwash.dibo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("carwash.dibo.webConfig")
public class DiboApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiboApplication.class, args);
    }
}
