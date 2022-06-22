package carwash.dibo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AutoChemistryStatus {
    REFUELED("Заправить в бочку"),
    PURCHASE("Добавить на склад");

    private final String name;
}
