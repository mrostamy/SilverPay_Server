package com.mydomomain.silverpay.repository.mainRepository;

import com.mydomomain.silverpay.model.TicketContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITicketContentRepository extends JpaRepository<TicketContent,String> {
}
