package carwash.dibo;

import carwash.dibo.integration.NatureliaWaterOrderProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootApplication
@ConfigurationPropertiesScan("carwash.dibo.config")
public class DiboApplication {
    public static void main(String[] args) {
        SpringApplication.run(DiboApplication.class, args);
    }
}
