package carwash.dibo.integration.naturelia;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface NatureliaOrderService {

    /**
     * Place an order for water from naturelia.net
     * @return String message and HttpStatus
     */
    ResponseEntity<String> placeOrder();
}
