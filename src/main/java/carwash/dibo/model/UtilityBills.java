package carwash.dibo.model;

import lombok.*;

import javax.persistence.*;
import java.time.Month;
import java.util.Date;

@Entity
@Data
@Table(name = "utility_bills")
public class UtilityBills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfAdd;

    @Enumerated
    private Month month;

    private int bigWaterMeterValue;
    private int smallWaterMeterValue;
    private int electricalMeterValue;

    private int costByBigWaterMeter;
    private int costBySmallWaterMeter;
    private int costByElectricalMeter;
}
