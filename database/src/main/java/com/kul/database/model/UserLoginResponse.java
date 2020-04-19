package com.kul.database.model;

import com.kul.database.constants.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String username;
    private String firstName;
    private String lastName;
    private AuthorityEnum authority;
}
