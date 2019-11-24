package com.t4.LiveServer.repository;


import com.t4.LiveServer.model.Stream;
import com.t4.LiveServer.model.StreamType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StreamRepository extends JpaRepository<Stream, Integer> {
    List<Stream> findAllByStreamTypeInAndStatus(List<StreamType> streamTypes, Integer status, Pageable pageable);
    List<Stream> findByStreamNameContaining(String streamName);
    List<Stream> findByStreamNameContainingAndStreamTypeIn(String streamName, List<StreamType> streamTypes);
    List<Stream> findByStreamTypeIn(List<StreamType> streamTypes);

}
