package com.bjpowernode.crm.workbench.web.controller;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.vo.PageinatonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserService userService;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public List<User> getUserList(){
        List<User> userList = userService.getUserList();
        return userList;
    }

    @ResponseBody
    @RequestMapping("/saveActivity.do")
    public Map<String,Object> saveActivity(Activity activity, HttpServletRequest request){
        String id = UUIDUtil.getUUID();
        String creatTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        activity.setId(id);
        activity.setCreateTime(creatTime);
        activity.setCreateBy(createBy);
        int result = activityService.save(activity);
        Map<String,Object> map = new HashMap<>();

        if(result==1){
            map.put("success",true);
        }else {
            map.put("success",false);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/pageList.do")
    public PageinatonVO<Activity> pageList(Integer pageNo, Integer pageSize, String name,
                                           String owner, String startDate, String endDate){
        Integer skipSize = (pageNo-1)*pageSize;
        Map<String,Object> map = new HashMap<>();
        map.put("pageNo",pageNo);
        map.put("pageSize",pageSize);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipSize",skipSize);

        PageinatonVO<Activity> pageinatonVO = activityService.pageList(map);

        return pageinatonVO;
    }

    @ResponseBody
    @RequestMapping("/delete.do")
    public Map<String, Object> delete(String[] id){
        boolean success = activityService.delete(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success",success);
        return map;
    }

    @ResponseBody
    @RequestMapping("/getUserListAndActivity.do")
    public Map<String,Object> getUserListAndActivity(String id){
        Map<String,Object> map = new HashMap<>();
        List<User> users = userService.getUserList();
        Activity activity = activityService.selectById(id);
        map.put("userList", users);
        map.put("activity",activity);
        return map;
    }

    @ResponseBody
    @RequestMapping("/updateById.do")
    public Map<String,Object> updateById(Activity activity){
        Map<String,Object> map = new HashMap<>();
        boolean result = activityService.updateById(activity);
        map.put("success",result);
        return map;
    }

    @RequestMapping("/viewDetailById.do")
    public ModelAndView viewDetailById(String id){
        ModelAndView mv = new ModelAndView();
        Activity activity = activityService.selectUserAndActivity(id);
        mv.addObject("activity", activity);
        mv.setViewName("detail.jsp");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getRemarkListByaId.do")
    public List<ActivityRemark> getRemarkListByaId(String aId){
        List<ActivityRemark> activityRemarks = activityService.getRemarkListByaId(aId);
        return activityRemarks;
    }

    @ResponseBody
    @RequestMapping("/deleteRemarkById.do")
    public Map<String, Object> deleteRemarkById(String id){
        Map<String,Object> map = activityService.deleteRemarkById(id);
        return map;
    }

    @ResponseBody
    @RequestMapping("/saveRemark.do")
    public Map<String,Object> saveRemark(ActivityRemark activityRemark){
        Map<String, Object> map = activityService.saveRemark(activityRemark);
        return map;
    }

    @ResponseBody
    @RequestMapping("/updateRemarkById.do")
    public Map<String,Object> updateRemarkById(ActivityRemark activityRemark){
        Map<String,Object> map = activityService.updateRemarkById(activityRemark);
        return map;
    }
}
