package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserLikeRepository extends JpaRepository<UserLike, Integer> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM user_like WHERE user_like.user_id=:userId AND user_like.stream_id=:streamId")
    UserLike checkingLikeStatus(@Param("userId") int userId, @Param("streamId") int streamId);

    @Query(nativeQuery = true,
            value = "DELETE FROM user_like WHERE user_like.user_id=:userId AND user_like.stream_id=:streamId")
    void deleteLikeStatus(@Param("userId") int userId, @Param("streamId") int streamId);
    void deleteByUserIdAndStreamId(int userId, int streamId);
}
