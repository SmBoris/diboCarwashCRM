package carwash.dibo.bills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UtilityMeterType {

    SMALL_WATER_METER("Маленький счетчик холодной воды"),
    BIG_WATER_METER("Большой счетчик холодной воды"),
    ELECTRICAL_METER("Счетчик электричества");

    private final String displayValue;

}
