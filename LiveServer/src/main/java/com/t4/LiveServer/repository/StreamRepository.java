package com.t4.LiveServer.repository;


import com.t4.LiveServer.model.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreamRepository extends JpaRepository<Stream, Integer> {
}
