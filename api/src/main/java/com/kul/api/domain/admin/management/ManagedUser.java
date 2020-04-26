package com.kul.api.domain.admin.management;

import com.kul.api.model.AuthorityEnum;
import lombok.Value;

@Value
public class ManagedUser {
    Long id;
    String username;
    String firstName;
    String lastName;
    Boolean enabled;
    AuthorityEnum authority;
}
