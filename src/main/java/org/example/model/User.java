package org.example.model;

import lombok.Data;
import org.example.util.Role;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(schema = "energy_platform")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @OneToMany(cascade = CascadeType.MERGE, fetch= FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private List<Device> associatedDevices;
}
