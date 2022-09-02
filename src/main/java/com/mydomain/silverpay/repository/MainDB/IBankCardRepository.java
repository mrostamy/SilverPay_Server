package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface IBankCardRepository extends JpaRepository<BankCard,String> {

    int countByUser_id(String userid);

    int countByApproveAndModifiedAtBefore(boolean approved, LocalDateTime date);

}
