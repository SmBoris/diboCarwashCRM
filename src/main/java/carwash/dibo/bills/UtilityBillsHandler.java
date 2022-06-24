package carwash.dibo.bills;

import carwash.dibo.model.UtilityBills;
import org.springframework.stereotype.Component;

@Component
public class UtilityBillsHandler {

    public UtilityBills updateValues(UtilityBills bills, UtilityMeterType type, int value, int cost){

        switch (type){

            case BIG_WATER_METER:
                bills.setBigWaterMeterValue(value);
                bills.setCostByBigWaterMeter(cost);
                return bills;

            case SMALL_WATER_METER:
                bills.setSmallWaterMeterValue(value);
                bills.setCostBySmallWaterMeter(cost);
                return bills;

            case ELECTRICAL_METER:
                bills.setElectricalMeterValue(value);
                bills.setCostByElectricalMeter(cost);

            default: throw new  IllegalArgumentException("UtilityMeter type error: " + type);
        }
    }
}
