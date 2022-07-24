package com.mydomomain.silverpay.repository.main;

import com.mydomomain.silverpay.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification,String> {
}
