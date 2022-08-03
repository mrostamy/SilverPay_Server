package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.Routes.V1.Routes;
import com.mydomomain.silverpay.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWalletRepository extends JpaRepository<Wallet, String> {

    int countByUser_id(String user_id);

    List<WalletView> findAllProjectBy() ;
}
