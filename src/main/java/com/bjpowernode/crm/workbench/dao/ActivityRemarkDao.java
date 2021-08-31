package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int getTotalByCondition(String[] id);

    int delete(String[] id);

    List<ActivityRemark> getRemarkListByaId(String aId);

    int deleteById(String id);

    int save(ActivityRemark activityRemark);

    int updateRemarkById(ActivityRemark activityRemark);
}
