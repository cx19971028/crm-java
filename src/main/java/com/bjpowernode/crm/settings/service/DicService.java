package com.bjpowernode.crm.settings.service;

import javax.servlet.ServletContextEvent;
import java.util.Map;

public interface DicService {
    Map<String, Object> getAll(ServletContextEvent event);
}
