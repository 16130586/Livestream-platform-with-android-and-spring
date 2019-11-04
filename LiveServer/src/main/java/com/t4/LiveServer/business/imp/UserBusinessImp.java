package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessImp implements UserBusiness {
    @Autowired
    private UserRepository userRepository;
    @Override
    // ?? :D ??
    public User login(String username, String password) {
        return userRepository.findById(1).get();
    }

}
