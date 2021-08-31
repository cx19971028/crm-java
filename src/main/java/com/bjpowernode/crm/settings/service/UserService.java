package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    User login(String loginAct, String password, String ip) throws LoginException;

    List<User> getUserList();

    User getUserNameById(String owner);
}
