package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.User;


import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.settings.service.impl.UserServiceImpl;
import com.bjpowernode.crm.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/setting/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/userlogin.do")
    @ResponseBody
    public Map<String,Object> userLogin(String loginAct, String loginPwd, HttpServletRequest request){

        // 讲密码明文转成密文
        String password = MD5Util.getMD5(loginPwd);
        // 接收浏览器的IP地址
        String ip = request.getRemoteAddr();
//        System.out.println("------------IP:" + ip);
        Map<String,Object> result = new HashMap<>();

       try {
           User user = userService.login(loginAct,password,ip);
           request.getSession().setAttribute("user", user);
           result.put("success",true);
       }catch (Exception e){
           e.printStackTrace();
           // 登陆失败
           String msg = e.getMessage();
           result.put("success",false);
           result.put("msg", msg);
       }

        return result;
    }
}
