package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {

    boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);


}
