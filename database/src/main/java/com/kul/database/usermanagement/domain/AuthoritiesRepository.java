package com.kul.database.usermanagement.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {
    Authorities findByUsername(String username);
}
