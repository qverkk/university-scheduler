package com.kul.database.usermanagement.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    ArrayList<User> findAllByAuthority(AuthorityEnum authority);
}
