package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.dao.DicTypeDao;
import com.bjpowernode.crm.settings.dao.DicValueDao;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class DicServiceImpl implements DicService {
    @Autowired
    private DicTypeDao dicTypeDao;
    @Autowired
    private DicValueDao dicValueDao;

    @Override
    public Map<String, Object> getAll(ServletContextEvent event) {
        ApplicationContext context =
                WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
        dicTypeDao = context.getBean(DicTypeDao.class);
        dicValueDao = context.getBean(DicValueDao.class);
        Map<String,Object> map = new HashMap<>();
        List<DicType> dicTypes = dicTypeDao.selectAll();

        for(DicType dicType:dicTypes){
            String code = dicType.getCode();
            List<DicValue> dicValues = dicValueDao.selectByCode(code);
            map.put(code,dicValues);
        }
        return map;
    }
}
