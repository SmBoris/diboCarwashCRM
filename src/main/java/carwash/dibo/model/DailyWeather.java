package carwash.dibo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class DailyWeather {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private double averageWindSpeed;
    private double averageTemp;
    private String averageCondition;
}
