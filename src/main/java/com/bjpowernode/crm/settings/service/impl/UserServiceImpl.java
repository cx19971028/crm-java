package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.exception.LoginException;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String,String> userMsg = new HashMap<>();
        userMsg.put("loginAct", loginAct);
        userMsg.put("loginPwd", loginPwd);

        User user = userDao.login(userMsg);


        if(user == null){
            throw new LoginException("用户名或密码错误");
        }

        // 判断锁定状态
        if("0".equals(user.getLockState())){
            throw new LoginException("账户已锁定");
        }

        // 判断失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0){
            throw new LoginException("账户已失效");
        }

        // 判断Ip
        if(!user.getAllowIps().contains(ip)){
            throw new LoginException("Ip访问受限");
        }

        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userDao.getUserList();
        return userList;
    }

    @Override
    public User getUserNameById(String owner) {
        User user = userDao.getUserNameById(owner);
        return user;
    }
}
