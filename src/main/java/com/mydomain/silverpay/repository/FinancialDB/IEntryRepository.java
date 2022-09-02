package com.mydomain.silverpay.repository.FinancialDB;

import com.mydomain.silverpay.data.model.financialDB.accountant.Entry;
import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEntryRepository extends JpaRepository<Entry, String> {

    int countByApprove(boolean approved);

    int countByApproveAndRejectAndPayed(boolean approved, boolean rejected, boolean payed);

    List<Entry> findTop10ByUser_Id(String userId);

    @Query("SELECT SUM(e.price) FROM Entry e WHERE e.userId=:userId and e.isPayed = true")
    int findSumOfPrice(@Param(value = "userId") String userId);

    @Query("SELECT SUM(e.price) FROM Entry e WHERE e.userId=:userId and e.isPayed=true and e.modifiedAt=:date")
    int findSumOfPriceAtDate(@Param(value = "userId") String userId, @Param(value = "userId") LocalDateTime dateTime);

//    @Query("SELECT SUM(e.price) FROM Entry e WHERE e.userId=:userId and e.isPayed=true and e.modifiedAt=:date")
//    int findSumOfPriceAtDate(@Param(value = "userId") String userId, @Param(value = "userId") LocalDateTime dateTime);
//

}
