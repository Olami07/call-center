package com.automation.callcenter.employees.entity;

import com.automation.callcenter.team.entity.Teams;
import com.automation.callcenter.units.entity.Units;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Entity
@Data
@Table(name="employees", uniqueConstraints = {@UniqueConstraint(
        name = "email_unique",
        columnNames = "email"
), @UniqueConstraint(
        name = "avayaId_unique",
        columnNames = "avayaId"
), @UniqueConstraint(
        name = "empId_unique",
        columnNames = "staffId"
), @UniqueConstraint(
        name = "username_unique",
        columnNames = "username"
)}
)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employees {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    @Column( name = "id", updatable = false)
    private Long id;
    @Column( name = "staffId", length = 10, nullable = false)
    private String staffId;
    @Column( name = "avayaId", nullable = false)
    private Long avayaId;
    @Column( name = "Username", nullable = false)
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String team;
    private String phone;
    private String language;

    private String status;
    @ManyToOne
    @JoinColumn(name = "Team_id")
    private Teams teams;
    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Units units;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    private Date modified;

}
