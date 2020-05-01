package com.kul.database.usermanagement.domain;

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

    public boolean canEnableUsers() {
        return authority == AuthorityEnum.ADMIN || authority == AuthorityEnum.DZIEKANAT;
    }

    public boolean canDeleteUsers() {
        return authority == AuthorityEnum.ADMIN;
    }

    public boolean canDisableUsers() {
        return authority == AuthorityEnum.ADMIN || authority == AuthorityEnum.DZIEKANAT;
    }

    public boolean hasAccessToAllUserData() {
        return authority == AuthorityEnum.ADMIN;
    }

    public boolean canUpdatePreferencesOf(User user) {
        return authority == AuthorityEnum.ADMIN ||
                authority == AuthorityEnum.DZIEKANAT ||
                user.getId().equals(id);
    }
}
