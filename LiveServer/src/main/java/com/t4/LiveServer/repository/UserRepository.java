package com.t4.LiveServer.repository;


import com.t4.LiveServer.model.Notification;
import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    User findByGmail(String gmail);
    @Query("select u.notifications from User u where u.userId = :userId")
    List<Notification> findNotificationById(@Param("userId") int userId);
    List<User> findBySubscribersContaining(User user);
    
	@Modifying
	@Transactional
	@Query(nativeQuery = true,
	 value = "UPDATE user SET nick_name = :newName , description = :newDescription WHERE user_id = :userID ")
	int repoUpdateAbout(@Param("userID") int userID, @Param("newName") String newName,@Param("newDescription") String newDescription);
	
	@Query(nativeQuery = true,value="select u.* from user u inner join subscriber as s on u.user_id = s.subscriber_id where s.publisher_id = :userID")
	List<User> repoGetSubscribedChannelByUserID(@Param("userID") int userID);
	User findByStreamsContains(Stream stream);
}
