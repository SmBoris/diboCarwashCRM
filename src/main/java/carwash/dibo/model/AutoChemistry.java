package carwash.dibo.model;

import carwash.dibo.common.AutoChemistryStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "auto_chemistry")
public class AutoChemistry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String name;
    private int quantityToChange;

    @Enumerated
    private AutoChemistryStatus status;

    @ManyToOne
    private User user;
    private int currentQuantity;

    public boolean isPurchase(){
        return getStatus().equals(AutoChemistryStatus.PURCHASE);
    }
}
