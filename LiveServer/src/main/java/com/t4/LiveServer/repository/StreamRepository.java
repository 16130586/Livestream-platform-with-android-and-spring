package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.Stream;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StreamRepository extends JpaRepository<Stream, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT s.* FROM stream as s inner join stream_type as st " +
                    "on s.stream_id = st.stream_id inner join types as t on t.type_id = st.type_id " +
                    "where t.type_name in :streamTypes and s.stream_status = :status group by s.stream_id having count(s.stream_id) = :countType")
    List<Stream> findAllByStreamTypeInAndStatus(@Param("streamTypes") List<String> streamTypes, @Param("status") Integer status, @Param("countType") Integer countType, Pageable pageable);
    List<Stream> findByTitleContaining(String streamName, Pageable pageable);
    @Query(nativeQuery = true,
            value = "SELECT s.* FROM stream as s inner join stream_type as st " +
                    "on s.stream_id = st.stream_id inner join types as t on t.type_id = st.type_id " +
                    "where t.type_name in :streamTypes and s.title like %:streamName% group by s.stream_id having count(s.stream_id) = :countType")
    List<Stream> findByStreamNameAndStreamType(@Param("streamName") String streamName, @Param("streamTypes") List<String> streamTypes, @Param("countType") Integer countType, Pageable pageable);
    @Query(nativeQuery = true,
            value = "SELECT s.* FROM stream as s inner join stream_type as st " +
                    "on s.stream_id = st.stream_id inner join types as t on t.type_id = st.type_id " +
                    "where t.type_name in :streamTypes group by s.stream_id having count(s.stream_id) = :countType")
    List<Stream> findByStreamType(@Param("streamTypes") List<String> streamTypes, @Param("countType") Integer countType, Pageable pageable);

	@Query(nativeQuery = true,
	 value = "SELECT s.* FROM stream as s inner join stream_type as st " +
	  "on s.stream_id = st.stream_id inner join types as t on t.type_id = st.type_id inner join user as u on u.user_id = s.owner_id " +
	  "where u.user_id = :userID and st.type_id = :typeID " )
	List<Stream> repoListStreamByTypeOfUser(@Param("userID") int userID,@Param("typeID") int typeID);
	
	@Query(nativeQuery = true,
	 value = "SELECT s.* FROM stream as s inner join favourite_saved as fs " +
		  "on s.stream_id = fs.stream_id " +
	  "where fs.user_id = :userID " )
	List<Stream> repoGetWatchedStreamsByUserID(@Param("userID") int userID);

	@Query(nativeQuery = true,
	value = "SELECT s.* FROM stream as s left join ranking as r on s.owner_id = r.user_id where s.stream_status != :streamStatus and r.month = :month and r.year = :year order by r.point desc, s.start_time asc limit :skip, :pageSize")
    List<Stream> getRecommendForCookieUser(@Param("streamStatus") int streamStatus, @Param("month") int month, @Param("year") int year, @Param("skip") int skip, @Param("pageSize") int pageSize);

}
