package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketRepository extends JpaRepository<Ticket,String> {
}