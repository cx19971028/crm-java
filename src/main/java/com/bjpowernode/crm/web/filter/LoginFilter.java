package com.bjpowernode.crm.web.filter;

import com.bjpowernode.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
//        System.out.println(path);
        if("/login.jsp".equals(path) || "/setting/user/userlogin.do".equals(path)){
            filterChain.doFilter(request,response);
        }else {
            User user = (User) request.getSession().getAttribute("user");
            if(user != null){
                filterChain.doFilter(request,response);
            }
            else {
                /**
                 * 请求转发： 需要特殊的绝对路径,不需要/项目名，也称内部转发
                 *       /login.jsp
                 * 重定向需要绝对地址,  /项目名
                 *      /crm/login.jsp
                 */
                response.sendRedirect("/crm/login.jsp");
            }
        }


    }

    @Override
    public void destroy() {

    }
}
