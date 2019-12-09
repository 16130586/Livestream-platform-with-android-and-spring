package com.t4.LiveServer.repository;


import com.t4.LiveServer.model.Notification;
import com.t4.LiveServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    User findByGmail(String gmail);
    @Query("select u.notifications from User u where u.userId = :userId")
    List<Notification> findNotificationById(@Param("userId") int userId);
}
