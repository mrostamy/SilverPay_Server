package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket,String> {

    int countByClosed(boolean closed);

    int countByUser_IdAndClosed(String user_id, boolean closed);

    List<Ticket> findTop10ByUser_Id(String userId);
}
