package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.model.User;

public interface UserBusiness {
    User login(String username, String password);
}
