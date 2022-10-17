package org.example.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "energy_platform")
public class Device {

    @Id
    @GeneratedValue
    @Column(name="device_id")
    private Long deviceId;
    private String description;
    private String address;
    private Float maximumHourlyConsumption;

    @OneToMany(cascade = CascadeType.MERGE, fetch= FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="device_id", referencedColumnName = "device_id")
    private List<Measures> measures;

}
