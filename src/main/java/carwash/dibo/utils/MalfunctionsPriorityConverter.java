//package carwash.dibo.utils;
//
//import carwash.dibo.common.MalfunctionsPriority;
//
//import javax.persistence.AttributeConverter;
//import javax.persistence.Convert;
//import javax.persistence.Converter;
//import java.util.stream.Stream;
//
//@Converter
//public class MalfunctionsPriorityConverter implements AttributeConverter<MalfunctionsPriority, String> {
//
//    @Override
//    public String convertToDatabaseColumn(MalfunctionsPriority malfunctionsPriority) {
//        if (malfunctionsPriority == null){
//            return null;
//        }
//        return malfunctionsPriority.getName();
//    }
//
//    @Override
//    public MalfunctionsPriority convertToEntityAttribute(String name) {
//
//        if (name == null){
//            return null;
//        }
//
//        return Stream.of(MalfunctionsPriority.values())
//                .filter(n -> n.getName().equals(name))
//                .findFirst()
//                .orElseThrow(IllegalArgumentException::new);
//    }
//}
