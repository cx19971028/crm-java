package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("测试拦截器");
        User user = (User) request.getSession().getAttribute("user");
        if(user != null){
            return true;
        }
        else {
            /**
             * 请求转发： 需要特殊的绝对路径,不需要/项目名，也称内部转发
             *       /login.jsp
             * 重定向需要绝对地址,  /项目名
             *      /crm/login.jsp
             */
            response.sendRedirect("/crm/login.jsp");
            return false;

        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
