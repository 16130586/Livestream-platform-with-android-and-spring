package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.model.Notification;
import com.t4.LiveServer.model.User;
import com.t4.LiveServer.validation.form.RegistryForm;

import java.util.List;

public interface UserBusiness {
    String login(String username, String password);
    User registry(RegistryForm registryForm);
    boolean checkExistsUsername(String username);
    User getUserById(int id);
    User getUserByGmail(String gmail);
    void saveUser(User user);
    List<Notification> getNotification(int userId);
    boolean upgradePremium(int userId, int subscriptionId, int number, double amount);
    void deleteNotification(Integer id);
    List<User> getSubscription(User user, int offset, int limit);
}
