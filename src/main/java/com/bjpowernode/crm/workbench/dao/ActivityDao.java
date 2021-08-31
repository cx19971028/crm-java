package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    Integer delete(String[] id);

    Activity selectById(String id);

    int updateById(Activity activity);

    Activity selectActivityAndUser(String id);

    List<Activity> selectAllActivityForBound(String cId);

    List<Activity> selectActByName(Map<String,String> pMap);

    List<Activity> selectAllActivityForBoundBycId(String cId);

    List<Activity> selectActByNameInCId(Map<String, String> pMap);

    List<Activity> getActivityList();
}
