package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
