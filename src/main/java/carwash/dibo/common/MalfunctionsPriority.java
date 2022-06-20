package carwash.dibo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MalfunctionsPriority {
    HIGHEST("Высокий"),
    MEDIUM("Средний"),
    LOW("Низкий");

    private final String name;
}
