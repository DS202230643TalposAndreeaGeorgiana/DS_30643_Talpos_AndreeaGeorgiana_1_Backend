package org.example.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class MeasuresDTO implements Serializable {
    private Long measureId;
    private Long deviceId;
    private Float energyConsumption;
    private Timestamp timestamp;
}
