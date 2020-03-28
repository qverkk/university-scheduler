package com.kul.database.repository;

import com.kul.database.model.Authority;
import com.kul.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    ArrayList<User> findAllByAuthority(Authority authority);
}
