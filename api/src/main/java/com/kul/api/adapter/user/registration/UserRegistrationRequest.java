package com.kul.api.adapter.user.registration;

import com.kul.api.model.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Boolean enabled;
    private AuthorityEnum authority;
}
