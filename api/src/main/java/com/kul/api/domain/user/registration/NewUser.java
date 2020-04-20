package com.kul.api.domain.user.registration;

import com.kul.api.model.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private AuthorityEnum authority;
}
