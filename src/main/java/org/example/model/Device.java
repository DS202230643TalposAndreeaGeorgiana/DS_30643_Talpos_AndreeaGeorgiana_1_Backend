package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue
    @Column(name="device_id")
    private Long deviceId;
    private String description;
    private String address;
    private Float maximumHourlyConsumption;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device")
    private List<Measures> measures;

    @JsonIgnore
    @ManyToOne( fetch= FetchType.LAZY)
    private User user;

    public void addMeasure(Measures measures) {
        this.measures.add(measures);
        measures.setDevice(this);
    }

    public void removeMeasure(Measures measures) {
        this.measures.remove(measures);
        measures.setDevice(null);
    }

}
