package com.kul.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponse {
    private String username;
    private String firstName;
    private String lastName;
    private AuthorityEnum authority;
}
