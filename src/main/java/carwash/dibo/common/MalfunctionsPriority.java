package carwash.dibo.common;

import carwash.dibo.utils.EnumConverter;
import carwash.dibo.utils.PersistableEnum;
import com.sun.xml.bind.v2.model.core.EnumConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MalfunctionsPriority implements PersistableEnum<String> {

    HIGHEST("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий");

    private final String name;

    @Override
    public String getValue() {
        return this.name;
    }

    public static class Converter extends EnumConverter<MalfunctionsPriority, String> {

        public Converter() {
            super(MalfunctionsPriority.class);
        }
    }
}
