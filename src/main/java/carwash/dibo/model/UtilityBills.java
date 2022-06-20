package carwash.dibo.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "utility_bills")
public class UtilityBills {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Date dateOfAdd;

    private float bigWaterMeterValue;
    private float smallWaterMeterValue;
    private float electricalMeterValue;

    private int costByBigWaterMeter;
    private int costBySmallWaterMeter;
    private int costByElectricalMeter;
}
