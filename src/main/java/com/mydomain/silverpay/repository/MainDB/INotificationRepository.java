package com.mydomain.silverpay.repository.MainDB;

import com.mydomain.silverpay.data.model.mainDB.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationRepository extends JpaRepository<Notification,String> {
}
