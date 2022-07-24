package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITokenRepository extends JpaRepository<Token, String> {
}
