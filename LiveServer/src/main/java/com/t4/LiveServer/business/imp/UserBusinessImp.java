package com.t4.LiveServer.business.imp;

import com.t4.LiveServer.business.interfaze.UserBusiness;
import com.t4.LiveServer.business.interfaze.file.FileBusiness;
import com.t4.LiveServer.jwt.JwtProvider;
import com.t4.LiveServer.model.Notification;
import com.t4.LiveServer.model.PaySubscription;
import com.t4.LiveServer.model.Subscription;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.model.security.CustomUserDetails;
import com.t4.LiveServer.repository.PaySubscriptionRepository;
import com.t4.LiveServer.repository.SubscriptionRepository;
import com.t4.LiveServer.model.NotificationStatus;
import com.t4.LiveServer.repository.NotificationRepository;
import com.t4.LiveServer.repository.UserRepository;
import com.t4.LiveServer.util.StringUtils;
import com.t4.LiveServer.validation.form.RegistryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserBusinessImp implements UserBusiness {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider tokenProvider;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    PaySubscriptionRepository paySubscriptionRepository;
    @Autowired
    FileBusiness fileBusiness;

    private static final String ABSOLUTE_PATH_IMAGE_STORAGE = (new File("src/main/resources/image")).getAbsolutePath() + "\\";
    private static final String DEFAULT_AVATAR_PATH = "/images/ava_default.jpg";

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
        String fileName = StringUtils.randomString() + ".jpg";
        String path = ABSOLUTE_PATH_IMAGE_STORAGE + fileName;
        User user = new User();
        user.setUserName(registryForm.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(registryForm.getPassword())); // encode password
        user.setGmail(registryForm.getGmail());
        user.setNickName(registryForm.getNickName());
        if (registryForm.getAvatar() != null && !registryForm.getAvatar().isEmpty())
            if (fileBusiness.base64ToImage(registryForm.getAvatar(), path))
                user.setAvatar("/images/" + fileName);
            else
                user.setAvatar(DEFAULT_AVATAR_PATH);
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
        List<Notification> subResult = userRepository.findNotificationById(userId);
        List<Notification> result = new ArrayList<>();
        for (Notification notify: subResult) {
            if (notify.getStatus() != NotificationStatus.DELETE)
                result.add(notify);
        }
        return result;
    }

    @Override
    public boolean upgradePremium(int userId, int subscriptionId, int number, double amount) {
        try {
            Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
            User user = userRepository.findById(userId).get();
            PaySubscription paySubscription = new PaySubscription();
            paySubscription.setSubscription(subscription);
            paySubscription.setAmount(amount);
            Calendar calendar = Calendar.getInstance();
            paySubscription.setStartTime(calendar.getTime());
            calendar.add(Calendar.MONTH, number);
            paySubscription.setEndTime(calendar.getTime());
            paySubscriptionRepository.save(paySubscription);
            user.addPaySubscription(paySubscription);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void deleteNotification(Integer id) {
        Notification notification = notificationRepository.findById(id).get();
        notification.setStatus(NotificationStatus.DELETE);
        notificationRepository.save(notification);
    }

    @Override
    public List<User> getSubscription(User user, int offset, int limit) {
        if (offset <= 0)
            offset = 0;
        if (limit <= 0)
            limit = 5;

        List<User> userList = userRepository.findBySubscribersContaining(user);
        List<User> result = new ArrayList<>();

        int startLength = offset - 1;
        if (startLength > userList.size()) {
            result = null;
        } else {
            for (int i = startLength, picked = 0; i < userList.size(); i++, picked++) {
                if (picked < limit) {
                    result.add(userList.get(i));
                }
            }
        }
        return result;
    }
    
	@Override
	public boolean  updateAbout(Integer userID,String newName,String newDescription){
		int checkResult = userRepository.repoUpdateAbout(userID, newName, newDescription);
		if(checkResult>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	@Override
	public List<User> getSubscribedChannelByUserID(int userID, int offset, int limit) {
		if (offset <= 0)
			offset = 0;
		if (limit <= 0)
			limit = 5;
		
		List<User> userList = userRepository.repoGetSubscribedChannelByUserID(userID);
		List<User> result = new ArrayList<>();
		
		int startLength = offset - 1;
		if (startLength > userList.size()) {
			result = null;
		} else {
			for (int i = startLength, picked = 0; i < userList.size(); i++, picked++) {
				if (picked < limit) {
					result.add(userList.get(i));
				}
			}
		}
		return result;
	}
}
