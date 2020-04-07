package com.kul.database.model;

import com.kul.database.constants.AuthorityEnum;
import com.kul.database.constraints.ValidPassword;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Entity(name = "users")
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Email should be valid")
    private String username;

    @ValidPassword
    @NotEmpty
    private String password;

    @Size(min = 1, max = 30, message = "First name length should be between 1 and 30")
    @Pattern(regexp = "^\\w+$")
    private String firstName;

    @Size(min = 1, max = 30, message = "Surname length should be between 1 and 30")
    @Pattern(regexp = "^\\w+$")
    private String lastName;

    private Boolean enabled;

    @NotNull
    private AuthorityEnum authority;
}
