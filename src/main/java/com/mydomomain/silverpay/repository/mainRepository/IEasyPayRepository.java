package com.mydomomain.silverpay.repository.mainRepository;

import com.mydomomain.silverpay.model.EasyPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEasyPayRepository extends JpaRepository<EasyPay, String> {
}
