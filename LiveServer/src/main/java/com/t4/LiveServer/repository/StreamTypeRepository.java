package com.t4.LiveServer.repository;

import com.t4.LiveServer.model.StreamType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamTypeRepository extends JpaRepository<StreamType, Integer> {
    StreamType findByTypeName(String typeName);
}
