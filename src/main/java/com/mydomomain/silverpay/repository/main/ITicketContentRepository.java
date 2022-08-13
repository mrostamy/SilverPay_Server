package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Ticket;
import com.mydomomain.silverpay.model.TicketContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketContentRepository extends JpaRepository<TicketContent,String> {
}
