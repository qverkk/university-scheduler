package com.kul.database.model;

import com.kul.database.constants.AuthorityEnum;
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
