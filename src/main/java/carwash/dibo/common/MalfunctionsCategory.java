package carwash.dibo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MalfunctionsCategory {
    INTERNAL_EQUIPMENT("Оборудование в техничке"),
    OUTDOOR_EQUIPMENT("Оборудование на улице"),
    SEWAGE_SYSTEM("Канализация"),
    VIDEO_SURVEILLANCE("Видеонаблюдение"),
    OUTDOOR_LIGHTING("Внешнее освещение"),
    ROAD_SURFACE("Дорожное покрытие"),
    VACUUM_CLEANER("Пылесосы"),
    CASH_REGISTER("Кассовый аппарат"),
    OTHER("Другое");

    private final String name;
}
