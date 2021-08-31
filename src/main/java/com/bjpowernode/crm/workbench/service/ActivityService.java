package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.vo.PageinatonVO;
import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int save(Activity activity);

    PageinatonVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] id);

    Activity selectById(String id);

    boolean updateById(Activity activity);

    Activity selectUserAndActivity(String id);

    List<ActivityRemark> getRemarkListByaId(String aId);

    Map<String, Object> deleteRemarkById(String id);

    Map<String, Object> saveRemark(ActivityRemark activityRemark);

    Map<String, Object> updateRemarkById(ActivityRemark activityRemark);

    List<Activity> selectAllActivityForBound(String cId);

    Map<String, Object> selectActByName(Map<String, String> pMap);

    List<Activity> selectAllActivityForBoundBycId(String cId);

    Map<String, Object> selectActByNameInCId(Map<String, String> pMap);

    List<Activity> getActivityList();
}
