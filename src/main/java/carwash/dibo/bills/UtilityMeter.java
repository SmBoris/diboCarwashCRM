package carwash.dibo.bills;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Enumerated;

@Data
@Component
public class UtilityMeter {

    @Enumerated
    UtilityMeterType type;

    private int value;
    private int cost;

    public int getTotalCost(int value, int cost){
        return value * cost;
    }
}
