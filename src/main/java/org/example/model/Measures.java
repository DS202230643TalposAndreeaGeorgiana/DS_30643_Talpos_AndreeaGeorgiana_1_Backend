package org.example.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(schema = "energy_platform")
public class Measures {

    @Id
    @GeneratedValue
    @Column(name="measure_id")
    private Long measureId;
    private Float energyConsumption;
    private Timestamp timestamp;
}
