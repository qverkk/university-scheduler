package com.kul.database.usermanagement.api;

import com.kul.database.usermanagement.domain.AuthorityEnum;
import lombok.Value;

@Value
public class AllUsersResponse {
    Long id;
    String username;
    String firstName;
    String lastName;
    Boolean enabled;
    AuthorityEnum authority;
}
