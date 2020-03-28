package com.kul.database.repository;

import com.kul.database.constants.AuthorityEnum;
import com.kul.database.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByAuthority(AuthorityEnum authority);
}
