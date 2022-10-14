package org.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(schema = "energy_platform")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String role;
}
