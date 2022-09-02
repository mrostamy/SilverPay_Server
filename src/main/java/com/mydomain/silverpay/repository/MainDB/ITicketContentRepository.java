package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.TicketContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketContentRepository extends JpaRepository<TicketContent,String> {
}
