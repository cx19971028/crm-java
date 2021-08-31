package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ContactsService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/workbench/transaction")
public class TransactionController {

    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @Autowired
    private ContactsService contactsService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private TranService tranService;

    @RequestMapping("/getUserList.do")
    public ModelAndView getUserList(){
        ModelAndView mv = new ModelAndView();
        List<User> users = userService.getUserList();
        mv.addObject("users",users);
        mv.setViewName("/workbench/transaction/save.jsp");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getActivityList.do")
    public List<Activity> getActivityList(){
        List<Activity> activities = activityService.getActivityList();
        return activities;
    }

    @ResponseBody
    @RequestMapping("/getContactsList.do")
    public List<Contacts> getContactsList(){
        List<Contacts> contacts = contactsService.getContactsList();
        return contacts;
    }

    @ResponseBody
    @RequestMapping("/getCustomerName.do")
    public List<String> getCustomerName(String name){
        List<String> strings = customerService.getCustomerName(name);
        return strings;
    }

    @ResponseBody
    @RequestMapping("/getPossibilityByStage.do")
    public String getPossibilityByStage(String stage, HttpServletRequest request){
        String possibility = ((Map<String,String>)(request.getServletContext().getAttribute("pMap"))).get(stage);
        return possibility;
    }
    @RequestMapping("/add.do")
    public ModelAndView add(Tran tran,String customerName,HttpServletRequest request){
        tran.setId(UUIDUtil.getUUID());
        tran.setCreateTime(DateTimeUtil.getSysTime());
        tran.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        User user = userService.getUserNameById(tran.getOwner());
        tran.setOwner(user.getName());
        Customer customer = customerService.getCustomerIdByName(customerName);
        tran.setCustomerId(customer.getId());
        boolean result = tranService.add(tran);
        if(result){
            return new ModelAndView("redirect:/workbench/transaction/index.jsp");
        }else {
            return null;
        }
    }
    @ResponseBody
    @RequestMapping("/detail.do")
    public ModelAndView detail(String id){
        ModelAndView mv = new ModelAndView();
        Tran t = tranService.getTranById(id);
        mv.addObject("tran",t);
        mv.setViewName("/workbench/transaction/detail.jsp");
        return mv;
    }

    @ResponseBody
    @RequestMapping("/getHistory.do")
    public List<TranHistory> getHistory(HttpServletRequest request,String id){
        List<TranHistory> tranHistories = tranService.getHistory(id);
        Map<String, String> map= (Map<String, String>) request.getServletContext().getAttribute("pMap");
        for(TranHistory tranHistory:tranHistories){
            String stage = tranHistory.getStage();
            String possibility = map.get(stage);
            tranHistory.setPossibility(possibility);
        }
        return tranHistories;
    }

    @ResponseBody
    @RequestMapping("/getHistoryList.do")
    public List<Map<String, Object>> getHistoryList(){
        List<Map<String, Object>> list = tranService.getHistoryList();
        return list;
    }
}
