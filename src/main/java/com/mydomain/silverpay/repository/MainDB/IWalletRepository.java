package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, String> {

    int countByUser_id(String user_id);

    List<WalletView> findAllProjectBy() ;

    @Query("SELECT SUM(w.inventory) FROM Wallet w WHERE w.user.id=:userId")
    int findSumOfInventories(@Param(value = "userId") String userId);

    @Query("SELECT SUM(w.enterMoney) FROM Wallet w WHERE w.user.id=:userId")
    int findSumOfEnterMonies(@Param(value = "userId") String userId);

    @Query("SELECT SUM(w.exitMoney) FROM Wallet w WHERE w.user.id=:userId")
    int findSumOfExitMonies(@Param(value = "userId") String userId);


}
