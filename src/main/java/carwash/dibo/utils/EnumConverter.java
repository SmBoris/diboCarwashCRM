package carwash.dibo.utils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public abstract class EnumConverter<T extends Enum<T> & PersistableEnum<E>, E> implements AttributeConverter<T, E> {

    private final Class<T> clazz;

    public EnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    public E convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.getValue() : null;
    }

    public T convertToEntityAttribute(E dbData) {

        if (dbData == null) {
            return null;
        }

        return Stream.of(clazz.getEnumConstants())
                .filter(n -> n.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}



