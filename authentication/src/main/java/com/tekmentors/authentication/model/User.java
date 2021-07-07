package com.tekmentors.authentication.model;


import lombok.Data;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name="security_users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<Authority> authorities = Collections.emptyList();

}
