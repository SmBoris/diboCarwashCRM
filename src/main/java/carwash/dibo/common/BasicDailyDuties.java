package carwash.dibo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BasicDailyDuties {

    CLEANING_OF_THE_TERRITORY("Уборка территории"),
    CHECKING_OF_CHEMICAL_VOLUME("Проверка химии"),
    CASH_REGISTER("Кассовый аппарат"),
    HIGH_PRESSURE_GUN("Пистолеты высокого давления"),
    CAR_WASH_BRUSH("Щетки");

    private final String duty;

    @Override
    public String toString() {
        return this.duty;
    }
}
