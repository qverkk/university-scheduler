package com.kul.api.adapter.admin.management;

import com.kul.api.model.AuthorityEnum;
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
