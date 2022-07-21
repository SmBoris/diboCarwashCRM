package carwash.dibo.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Converter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class DateConverter {

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static List<String> getRussianMonth (){

         return  Arrays.stream(Month.values())
                .map(x -> getRussianMonthName(x.getValue()))
                .collect(Collectors.toList());
    }

    public static int getMonthIndex(String rusMonth){
        return getRussianMonth().indexOf(rusMonth) + 1;
    }

    public static String getRussianMonthName(int month){
        return Month.of(month)
                .getDisplayName(TextStyle.FULL_STANDALONE, new Locale("ru", "RU")).toUpperCase();
    }

    public static int getNowIntYear(){
        return Year.now().getValue();
    }

    public static int getNowIntMonth(){
        LocalDate today = LocalDate.now();
        return today.getMonthValue();
    }

    public static boolean previousMonthInNextYear(int month){
         return month == 12 && getNowIntMonth() == 1;
    }
}
