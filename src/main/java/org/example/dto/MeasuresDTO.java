package org.example.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeasuresDTO {
    private Long id;
    private Float energyConsumption;
    private Timestamp timestamp;
}
