package com.kul.database.model;

import com.kul.database.constants.AuthorityEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Authority {
    @Id
    private Long id;

    private AuthorityEnum authority;
}
