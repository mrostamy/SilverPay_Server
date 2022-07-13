package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);

    @Transactional
    @Modifying
    @Query("update users u  set u=:user")
    int update(User user);
}
