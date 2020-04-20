package com.kul.api.adapter.user.authorization;

import com.kul.api.model.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginWithTokenResponse {
    private String username;
    private String firstName;
    private String lastName;
    private AuthorityEnum authority;
}
