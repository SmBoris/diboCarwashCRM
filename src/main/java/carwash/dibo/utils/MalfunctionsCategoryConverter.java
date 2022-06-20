package carwash.dibo.utils;

import carwash.dibo.common.MalfunctionsCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class MalfunctionsCategoryConverter implements AttributeConverter<MalfunctionsCategory, String> {
    @Override
    public String convertToDatabaseColumn(MalfunctionsCategory malfunctionsCategory) {
        if (malfunctionsCategory == null){
            return null;
        }
        return malfunctionsCategory.getName();
    }

    @Override
    public MalfunctionsCategory convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }
        return Stream.of(MalfunctionsCategory.values())
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
        }
    }

