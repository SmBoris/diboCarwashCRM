package carwash.dibo.bills;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class UtilityMeter {

    UtilityMeterType type;

    private int value;
    private int cost;

    public int getTotalCost(int value, int cost){
        return value * cost;
    }
}
