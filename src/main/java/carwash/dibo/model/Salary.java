package carwash.dibo.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.time.Month;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private User user;

    private Month month;

    private int value;

    private boolean payed;

    private int workShiftCount;

    private int betPerDay;
}
