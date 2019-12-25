package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByStreamIdAndVideoTime(int streamId, int videoTime);
}
