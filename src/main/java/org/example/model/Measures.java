package org.example.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(schema = "energy_platform")
public class Measures {

    @Id
    @GeneratedValue
    private Long id;
    private Float energyConsumption;
    private Timestamp timestamp;

}
