package com.bjpowernode.crm.web.listener;

import com.bjpowernode.crm.settings.service.DicService;
import com.bjpowernode.crm.settings.service.impl.DicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.*;

public class SysInitListener extends ContextLoaderListener {
    @Autowired
    DicService dicService;
    /**
     * 启动服务器，获取数据字典
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        ApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
//获取bean
        dicService =  context.getBean(DicService.class);
//        super.contextInitialized(event);
        // 获取上下文域对象
//        System.out.println("监听器");
        ServletContext application = event.getServletContext();
        Map<String,Object> map = dicService.getAll(event);
        Set<String> keySet = map.keySet();
        for(String key:keySet){
            application.setAttribute(key,map.get(key));
        }

        // 处理属性配置文件
        // 解析配置文件
        Map<String, String> pMap = new HashMap<>();
        // 资源绑定器
        ResourceBundle resourceBundle = ResourceBundle.getBundle("conf/Stage2Possibility");
        Enumeration<String> e = resourceBundle.getKeys();
        while (e.hasMoreElements()){
            String key = e.nextElement();
            String value = resourceBundle.getString(key);
            pMap.put(key,value);
        }
        application.setAttribute("pMap",pMap);
    }
}
