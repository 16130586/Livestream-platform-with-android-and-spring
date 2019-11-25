package com.t4.LiveServer.repository;


import com.t4.LiveServer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
    boolean existsByUserName(String username);
    User findByGmail(String gmail);
}
