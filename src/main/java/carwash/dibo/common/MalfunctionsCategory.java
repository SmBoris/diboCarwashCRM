package carwash.dibo.common;

import carwash.dibo.utils.EnumConverter;
import carwash.dibo.utils.PersistableEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MalfunctionsCategory implements PersistableEnum<String> {

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

    @Override
    public String getValue() {
        return this.name;
    }

    public static class Converter extends EnumConverter<MalfunctionsCategory, String> {
        public Converter() {
            super(MalfunctionsCategory.class);
        }
    }
}
