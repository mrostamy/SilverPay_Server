package com.mydomain.silverpay.repository.FinancialDB;

import com.mydomain.silverpay.data.model.financialDB.accountant.Factor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IFactorRepository extends JpaRepository<Factor, String> {

    List<Factor> findTop10ByUserId(String userId);

    @Query("SELECT SUM(f.price) FROM Factor f WHERE f.userId=:userId and f.status=true")
    int findSumOfPrice(@Param(value = "userId") String userId);

    @Query("SELECT SUM(f.endPrice) FROM Factor f WHERE f.userId=:userId and f.status=true")
    int findSumOfEndPrice(@Param(value = "userId") String userId);

    @Query("SELECT SUM(f.price) FROM Factor f WHERE f.userId=:userId and f.status=true and f.createdAt=:date")
    int findSumOfPriceAtDate(@Param(value = "userId") String userId,@Param(value = "date") LocalDateTime dateTime);

    @Query("SELECT SUM(f.endPrice) FROM Factor f WHERE f.userId=:userId and f.status=true and f.createdAt=:date")
    int findSumOfEndPriceAtDate(@Param(value = "userId") String userId,@Param(value = "date") LocalDateTime dateTime);

    @Query("SELECT SUM(f.endPrice) FROM Factor f WHERE f.userId=:userId and f.status=true and f.kind=:kind")
    int findSumOfEndPriceWithKind(@Param(value = "userId") String userId,@Param(value = "kind") int kind);

}
