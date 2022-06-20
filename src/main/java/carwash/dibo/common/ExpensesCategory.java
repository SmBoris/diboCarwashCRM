package carwash.dibo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExpensesCategory {

    SPARE_PARTS("Запчасти"),
    HOUSEHOLD_GOODS("Хоз товары"),
    SERVICES_OF_ORGANIZATION("Услуги организаций"),
    SERVICES_OF_INDIVIDUALS("Услуги индивидуальных лиц"),
    FORCE_MAJOR("Форс мажор"),
    GARBAGE_COLLECTION("Вывоз мусора");

    private final String name;
}
