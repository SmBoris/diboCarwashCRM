package carwash.dibo.model;

import carwash.dibo.utils.DateConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Data
@Table(name = "utility_bills")
public class UtilityBills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAdd;

    private int month;

    private int bigWaterMeterValue;
    private int smallWaterMeterValue;
    private int electricalMeterValue;

    private int costByBigWaterMeter;
    private int costBySmallWaterMeter;
    private int costByElectricalMeter;

    public String getRussianMonthName(int month){
        return DateConverter.getRussianMonthName(month);
    }

    public List<String> getRussianMonth(){
        return DateConverter.getRussianMonth();
    }
}
