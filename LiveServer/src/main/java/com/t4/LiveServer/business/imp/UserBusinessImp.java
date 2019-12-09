package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.jwt.JwtProvider;
import com.t4.LiveServer.model.Notification;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.model.security.CustomUserDetails;
import com.t4.LiveServer.repository.UserRepository;
import com.t4.LiveServer.validation.form.RegistryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBusinessImp implements UserBusiness {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider tokenProvider;

    @Override
    public String login(String username, String password) {
        // Xác thực từ username và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        return jwt;
    }

    @Override
    public User registry(RegistryForm registryForm) {
        User user = new User();
        user.setUserName(registryForm.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(registryForm.getPassword())); // encode password
        user.setGmail(registryForm.getGmail());
        user.setNickName(registryForm.getNickName());
        userRepository.save(user);
        return user;
    }

    @Override
    public boolean checkExistsUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByGmail(String gmail) {
        return userRepository.findByGmail(gmail);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<Notification> getNotification(int userId) {
        return userRepository.findNotificationById(userId);
    }

}
