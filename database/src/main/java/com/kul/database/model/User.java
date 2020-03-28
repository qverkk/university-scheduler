package com.kul.database.model;

import lombok.*;

import javax.persistence.*;

@Entity(name = "scheduler_users")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    @OneToOne
    private Authority authority;
}
