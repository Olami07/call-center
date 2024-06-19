package com.automation.callcenter.units.entity;

import com.automation.callcenter.employees.entity.Employees;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Units {
    @Id
    @SequenceGenerator(
            name = "units_sequence",
            sequenceName = "units_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "units_sequence"
    )
    private Long id;
    private String unitName;
    private String unitCode;

    @OneToMany(mappedBy = "units")
    private List<Employees> employees;
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
