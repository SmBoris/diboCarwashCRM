package carwash.dibo.model;

import carwash.dibo.common.MalfunctionsCategory;
import carwash.dibo.common.MalfunctionsPriority;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date openDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date closedDate;

    @Convert(converter = MalfunctionsCategory.Converter.class)
    private MalfunctionsCategory category;

    @Convert(converter = MalfunctionsPriority.Converter.class)
    private MalfunctionsPriority priority;

    private String description;
    private boolean isResolved;

    @ManyToOne
    private User user;
}
