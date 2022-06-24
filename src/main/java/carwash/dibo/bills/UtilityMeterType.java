package carwash.dibo.bills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UtilityMeterType {

    SMALL_WATER_METER("Счетчик холодной воды"),
    BIG_WATER_METER("Счетчик горячей воды"),
    ELECTRICAL_METER("Счетчик электричества");

    private final String name;
}
