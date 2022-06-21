package carwash.dibo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "working_day")
public class WorkingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Date openDate;
    private Date closeDate;

    private int tenCoins;
    private int diboCoins;
    private int cashOnBox;
    private int nonCash;

    private boolean open;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
}


