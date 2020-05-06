package com.kul.database.usermanagement.api.login;

import com.kul.database.usermanagement.domain.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginWithTokenResponse {
    private String username;
    private String firstName;
    private String lastName;
    private AuthorityEnum authority;
}
