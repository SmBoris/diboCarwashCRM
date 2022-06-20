package carwash.dibo.model;

import carwash.dibo.common.MalfunctionsCategory;
import carwash.dibo.common.MalfunctionsPriority;
import carwash.dibo.utils.MalfunctionsCategoryConverter;
import carwash.dibo.utils.MalfunctionsPriorityConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "malfunctions")
public class Malfunctions {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private Date openDate;
    private Date closedDate;

    @Convert(converter = MalfunctionsCategoryConverter.class)
    private MalfunctionsCategory category;

    @Convert(converter = MalfunctionsPriorityConverter.class)
    private MalfunctionsPriority priority;

    private String description;
    private boolean isResolved;

    @ManyToOne
    private User user;
}
