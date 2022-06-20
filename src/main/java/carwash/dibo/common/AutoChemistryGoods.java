package carwash.dibo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AutoChemistryGoods {
    ACTIVE_FOAM("Активная Пена"),
    WAX("Воск"),
    PRESSURE_WASH_FOAM("Предварительная мойка");

    private final String name;
}
