package carwash.dibo.model;

import carwash.dibo.common.ExpenseCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "additional_expense")

public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated
    private ExpenseCategory category;

    private String description;

    private int cost;
}
