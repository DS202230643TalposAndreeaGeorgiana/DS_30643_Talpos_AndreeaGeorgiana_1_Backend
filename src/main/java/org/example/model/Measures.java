package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measures")
public class Measures {

    @Id
    @GeneratedValue
    @Column(name="measure_id")
    private Long measureId;
    private Float energyConsumption;
    private Timestamp timestamp;

    @JsonIgnore
    @ManyToOne( fetch= FetchType.LAZY)
    private Device device;
}
