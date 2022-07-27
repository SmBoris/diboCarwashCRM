package carwash.dibo.model;

import carwash.dibo.utils.DateConverter;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "utility_bills")
public class UtilityBills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private int month;
    private int year;

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
