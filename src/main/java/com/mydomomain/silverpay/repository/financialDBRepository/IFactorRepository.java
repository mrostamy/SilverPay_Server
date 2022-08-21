package com.mydomomain.silverpay.repository.financialDBRepository;

import com.mydomomain.silverpay.model.accountant.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFactorRepository extends JpaRepository<Factor,String> {
}
