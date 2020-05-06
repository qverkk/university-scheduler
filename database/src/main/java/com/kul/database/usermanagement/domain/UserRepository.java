package com.kul.database.usermanagement.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    ArrayList<User> findAllByAuthority(AuthorityEnum authority);
}
