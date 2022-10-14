package org.example.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(schema = "energy_platform")
public class Device {

    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private String address;
    private Float maximumHourlyConsumption;

}
