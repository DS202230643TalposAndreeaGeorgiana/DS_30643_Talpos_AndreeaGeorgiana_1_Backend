package org.example.dto;

import lombok.Data;
import org.example.model.Measures;

import java.util.List;

@Data
public class DeviceDTO {
    private Long id;
    private String description;
    private String address;
    private Float maximumHourlyConsumption;
    private List<Measures> measures;
}
