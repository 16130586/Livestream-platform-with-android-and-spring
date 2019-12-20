package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.StreamType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StreamTypeRepository extends JpaRepository<StreamType, Integer> {
	// Tuyen
	StreamType findByTypeName(String typeName);
	List<StreamType> findByTypeNameIn(List<String> listName);
	
	//Tuan
	@Query(nativeQuery = true,
	  value = "SELECT t.* FROM stream as s inner join stream_type as st " +
	  "on s.stream_id = st.stream_id inner join types as t on t.type_id = st.type_id inner join user as u on u.user_id = s.owner_id " +
	  "where u.user_id = :userID  " )
	List<StreamType> getListTypeByUserID(@Param("userID") int userID);
	
}
