package com.t4.LiveServer.business.interfaze;

import com.t4.LiveServer.model.User;
import com.t4.LiveServer.validation.form.RegistryForm;

public interface UserBusiness {
    String login(String username, String password);
    User registry(RegistryForm registryForm);
    boolean checkExistsUsername(String username);
}
