package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.vo.PageinatonVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl  implements ActivityService {
    @Autowired
    private ActivityDao activityDao;
    @Autowired
    private ActivityRemarkDao activityRemarkDao;

    @Override
    public int save(Activity activity) {
        int result = activityDao.save(activity);
        return result;
    }

    @Override
    public PageinatonVO<Activity> pageList(Map<String, Object> map) {
        int totalSize = activityDao.getTotalByCondition(map);
        List<Activity> activities = activityDao.getActivityListByCondition(map);
        PageinatonVO<Activity> pageinatonVO = new PageinatonVO<>();
        pageinatonVO.setTotalSize(totalSize);
        pageinatonVO.setDataList(activities);
        return pageinatonVO;

    }
    @Transactional
    @Override
    public boolean delete(String[] id) {

        int count1 = activityRemarkDao.getTotalByCondition(id);
        int count2 = activityRemarkDao.delete(id);
        if(count1!=count2){
            return false;
        }
        // 查询remark总记录条数
        // 比较删除的remark记录条数与查询的记录条数是否一致
        // 删除activity记录条数
        // 比较删除的activity记录条数与查询的记录条数是否一致
        int result = activityDao.delete(id);
        boolean flag = false;
        if(result == id.length){
            flag = true;
        }
        return flag;
    }

    @Override
    public Activity selectById(String id) {
        Activity activity = activityDao.selectById(id);
        return activity;
    }

    @Override
    public boolean updateById(Activity activity) {
        boolean flag = false;
        int result = activityDao.updateById(activity);
        if(result==1){
            flag = true;
        }
        return flag;
    }

    @Override
    public Activity selectUserAndActivity(String id) {
        Activity activity = activityDao.selectActivityAndUser(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByaId(String aId) {
        List<ActivityRemark> activityRemarks = activityRemarkDao.getRemarkListByaId(aId);
        return activityRemarks;
    }

    @Override
    public Map<String, Object> deleteRemarkById(String id) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = false;
        int result = activityRemarkDao.deleteById(id);
        if(result==1){
            flag = true;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Map<String, Object> saveRemark(ActivityRemark activityRemark) {
        Map<String, Object> map = new HashMap<>();
        // 生成id
        activityRemark.setId(UUIDUtil.getUUID());
        // 添加时间
        activityRemark.setCreateTime(DateTimeUtil.getSysTime());
        boolean flag = false;
        int reslt = activityRemarkDao.save(activityRemark);
        if(reslt==1){
            flag = true;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public Map<String, Object> updateRemarkById(ActivityRemark activityRemark) {
        Map<String,Object> map = new HashMap<>();
        boolean flag = false;
        activityRemark.setEditTime(DateTimeUtil.getSysTime());
        int res = activityRemarkDao.updateRemarkById(activityRemark);
        if(res==1){
            flag = true;
        }
        map.put("success",flag);
        return map;
    }

    @Override
    public List<Activity> selectAllActivityForBound(String cId) {
        List<Activity> activities = activityDao.selectAllActivityForBound(cId);
        return activities;
    }

    @Override
    public Map<String, Object> selectActByName(Map<String, String> pMap) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = false;
        List<Activity> activities = activityDao.selectActByName(pMap);
        if(activities.size()>0){
            flag = true;
        }
        map.put("success",flag);
        map.put("activities", activities);
        return  map;
    }

    @Override
    public List<Activity> selectAllActivityForBoundBycId(String cId) {
        List<Activity> activities = activityDao.selectAllActivityForBoundBycId(cId);
        return activities;
    }

    @Override
    public Map<String, Object> selectActByNameInCId(Map<String, String> pMap) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = false;
        List<Activity> activities = activityDao.selectActByNameInCId(pMap);
        if(activities.size()>0){
            flag = true;
        }
        map.put("success",flag);
        map.put("activities", activities);
        return  map;
    }

    @Override
    public List<Activity> getActivityList() {
        List<Activity> activities = activityDao.getActivityList();
        return activities;
    }
}
