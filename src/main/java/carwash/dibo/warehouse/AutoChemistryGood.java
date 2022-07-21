package carwash.dibo.warehouse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AutoChemistryGood {

    ACTIVE_FOAM("Активная Пена"),
    WAX("Воск"),
    PRESSURE_WASH_FOAM("Предварительная мойка");

    private final String name;

    public String getName(){
        return this.name;
    }
}
