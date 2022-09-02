package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface IGateRepository extends JpaRepository<Gate,String> {


    Gate findByWebsiteUrlAndWallet_User_Id(String url,String userId);


    int countByActiveAndModifiedAtBefore(boolean b, LocalDateTime plusDays);
}
