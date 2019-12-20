package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SubcribleRepository extends JpaRepository<Subscribe, Integer> {
	@Modifying
	@Transactional
	@Query(nativeQuery = true,
	  value = "INSERT INTO subscriber VALUES(:subscriberID,:publisherID)")
	int repoDoSubscribe(@Param("subscriberID") int subscriberID, @Param("publisherID") int publisherID);
	@Modifying
	
	@Transactional
	@Query(nativeQuery = true,
	 value = "DELETE subscriber.* FROM subscriber WHERE subscriber.subscriber_id =:subscriberID AND subscriber.publisher_id=:publisherID")
	int repoUndoSubscribe(@Param("subscriberID") int subscriberID, @Param("publisherID") int publisherID);
	@Modifying
	
	@Transactional
	@Query(nativeQuery = true,
	 value = "UPDATE user SET subscribe_total = subscribe_total+1 WHERE user_id = :publisherID")
	int repoAddSubTotal(@Param("publisherID") int publisherID);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true,
	 value = "UPDATE user SET subscribe_total = subscribe_total-1 WHERE user_id = :publisherID")
	int repoRemoveSubTotal(@Param("publisherID") int publisherID);
	
	@Query(nativeQuery = true,
	 value = "SELECT * FROM subscriber WHERE subscriber.subscriber_id =:subscriberID AND subscriber.publisher_id=:publisherID")
	Subscribe repoCheckSubscribe(@Param("subscriberID") int subscriberID, @Param("publisherID") int publisherID);
	
	
}
