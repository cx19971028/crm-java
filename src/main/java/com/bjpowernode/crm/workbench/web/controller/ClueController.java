package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @ResponseBody
    @RequestMapping("/getUserList.do")
    public List<User> getUserList(){
        List<User> users = userService.getUserList();
        return users;
    }

    @ResponseBody
    @RequestMapping("/saveClue.do")
    public Map<String,Object> saveClue(Clue clue, HttpServletRequest request){
        clue.setId(UUIDUtil.getUUID());
        clue.setCreateTime(DateTimeUtil.getSysTime());
        clue.setCreateBy(((User)(request.getSession().getAttribute("user"))).getName());
        Map<String,Object> map = clueService.saveClue(clue);
        return map;
    }

    @ResponseBody
    @RequestMapping("/detail.do")
    public ModelAndView selectById(String id){
        ModelAndView mv = new ModelAndView();
        Clue clue = clueService.selectById(id);
        mv.addObject("clue", clue);
        mv.setViewName("detail.jsp");
        return mv;
    }
    @ResponseBody
    @RequestMapping("/selectClueAndActivity.do")
    public List<Activity> SelectClueAndActivityRelation(String id){
        List<Activity> activities = clueService.SelectClueAndActivityRelation(id);
        return activities;
    }

    @ResponseBody
    @RequestMapping("/removeRelation.do")
    public Map<String,Object> removeRelation(String carId){
        Map<String, Object> map = new HashMap<>();
        boolean res = clueService.removeRelation(carId);
        map.put("success",res);
        return map;
    }

    @ResponseBody
    @RequestMapping("/selectAllActivityForBound.do")
    public List<Activity> selectAllActivityForBound(String cId){
        List<Activity> activities = activityService.selectAllActivityForBound(cId);
        return activities;
    }

    @ResponseBody
    @RequestMapping("/addRelation.do")
    public Map<String,Object> addRelation(String[] aId,String cId ){
        List<ClueActivityRelation> clueActivityRelations = new ArrayList<>();
        for(String s:aId){
            ClueActivityRelation a = new ClueActivityRelation();
            a.setId(UUIDUtil.getUUID());
            a.setClueId(cId);
            a.setActivityId(s);
            clueActivityRelations.add(a);
        }
        boolean res = clueService.addRelation(clueActivityRelations);
        Map<String,Object> map = new HashMap<>();
        map.put("success",res);

        return map;
    }

    @ResponseBody
    @RequestMapping("/selectActByName.do")
    public Map<String, Object> selectActByName(String name,String cId){
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("cId",cId);
        Map<String, Object> map = activityService.selectActByName(paramMap);
        return  map;
    }

    @ResponseBody
    @RequestMapping("/selectAllActivityForBoundBycId.do")
    public List<Activity> selectAllActivityForBoundBycId(String cId){
        List<Activity> activities = activityService.selectAllActivityForBoundBycId(cId);
        return activities;
    }

    @ResponseBody
    @RequestMapping("/selectActByNameInCId.do")
    public Map<String, Object> selectActByNameInCId(String name,String cId){
        Map<String,String> pMap = new HashMap<>();
        pMap.put("name",name);
        pMap.put("cId",cId);
        Map<String, Object> map = activityService.selectActByNameInCId(pMap);

        return map;
    }

    /**
     *
     * @param cId :线索Id
     * @param tran :交易的部分信息
     * @return：返回是否操作成功
     */
    @ResponseBody
    @RequestMapping("/convert.do")
    public ModelAndView convert(String cId, Tran tran, HttpServletRequest request){
        // 获取创建人
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        boolean result = clueService.convert(cId,tran,createBy);
        if(result){
            return new ModelAndView("redirect:/workbench/clue/index.jsp");
        }
        return null;
    }
}
