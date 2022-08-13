package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Gate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGateRepository extends JpaRepository<Gate,String> {


    Gate findByWebsiteUrlAndWallet_User_Id(String url,String userId);


}
