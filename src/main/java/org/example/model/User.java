package org.example.model;

import lombok.*;
import org.example.util.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Device> associatedDevices;

    public void addDevice(Device device) {
        associatedDevices.add(device);
        device.setUser(this);
    }

    public void removeDevice(Device device) {
        associatedDevices.remove(device);
        device.setUser(null);
    }

}
