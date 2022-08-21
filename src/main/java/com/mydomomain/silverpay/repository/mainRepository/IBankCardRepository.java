package com.mydomomain.silverpay.repository.mainRepository;

import com.mydomomain.silverpay.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBankCardRepository extends JpaRepository<BankCard,String> {

    int countByUser_id(String userid);

}
