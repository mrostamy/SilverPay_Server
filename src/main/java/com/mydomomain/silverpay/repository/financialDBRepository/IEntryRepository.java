package com.mydomomain.silverpay.repository.financialDBRepository;

import com.mydomomain.silverpay.model.accountant.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEntryRepository extends JpaRepository<Entry,String> {
}
