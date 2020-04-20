package com.kul.api.domain.user.authorization;

import com.kul.api.model.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String username;
    private String firstName;
    private String lastName;
    private AuthorityEnum authority;
}
