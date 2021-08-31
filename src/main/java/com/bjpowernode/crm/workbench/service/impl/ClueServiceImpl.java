package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {

    @Autowired
    private ClueDao clueDao;
    @Autowired
    private ClueRemarkDao clueRemarkDao;
    @Autowired
    private ClueActivityRelationDao clueActivityRelationDao;

    @Autowired
    private ContactsDao contactsDao;
    @Autowired
    private ContactsRemarkDao contactsRemarkDao;
    @Autowired
    private ContactsActivityRelationDao contactsActivityRelationDao;

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private CustomerRemarkDao customerRemarkDao;

    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Override
    public Map<String, Object> saveClue(Clue clue) {
        Map<String, Object> map = new HashMap<>();
        boolean flag = false;
        int result = clueDao.saveClue(clue);
        if(result==1){
            flag = true;
        }
        map.put("success", flag);
        return map;
    }

    @Override
    public Clue selectById(String id) {
        Clue clue = clueDao.selectById(id);
        return clue;
    }

    @Override
    public List<Activity> SelectClueAndActivityRelation(String id) {
        List<Activity> activities = clueDao.SelectClueAndActivityRelation(id);
        return activities;
    }

    @Override
    public boolean removeRelation(String carId) {
        boolean flag = false;
        int res = clueDao.removeRelation(carId);
        if(res == 1){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean addRelation(List<ClueActivityRelation> clueActivityRelations) {
        int count = 0;
        boolean flag = false;
        for(ClueActivityRelation c:clueActivityRelations){
            count += clueActivityRelationDao.addRelation(c);
        }

        if(count==clueActivityRelations.size()){
            flag = true;
        }
        return flag;
    }

    @Transactional
    @Override
    public boolean convert(String cId, Tran tran, String createBy) {
        String createTime = DateTimeUtil.getSysTime();
        boolean flag = true;
        // 1.获取线索的id, 通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.selectById(cId);

        // 2. 通过线索提取客户信息，当该客户不存在时，新建客户（根据公司名称精确匹配，判断该客户是否存在）
        String company = clue.getCompany();
        Customer customer = customerDao.selectByName(company);
        if(customer==null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setCreateBy(createBy);
            customer.setCreateTime(createTime);
            customer.setDescription(clue.getDescription());
            customer.setContactSummary(clue.getContactSummary());
            customer.setAddress(clue.getAddress());
            customer.setName(company);
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setOwner(clue.getOwner());
            customer.setPhone(clue.getPhone());
            customer.setWebsite(clue.getWebsite());
            int count1 = customerDao.add(customer);
            if(count1!=1){
                flag = false;
            }
        }

        // 3.通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setCreateTime(createTime);
        contacts.setAddress(clue.getAddress());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setDescription(clue.getDescription());
        contacts.setCreateBy(createBy);
        contacts.setAppellation(clue.getAppellation());
        contacts.setCustomerId(customer.getId());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        int count2 = contactsDao.add(contacts);
        if(count2!=1){
            flag = false;
        }

        // 4.线索备注转换成联系人备注以及客户备注
        List<ClueRemark> clueRemarks = clueRemarkDao.seletBycId(cId);
        for (ClueRemark clueRemark:clueRemarks){
            clueRemark.setCreateBy(createBy);
            clueRemark.setCreateTime(createTime);
            // 转成联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setCreateBy(clueRemark.getCreateBy());
            contactsRemark.setCreateTime(clueRemark.getCreateTime());
            contactsRemark.setContactsId(contacts.getId());
            contactsRemark.setEditFlag("0");
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            int count3 = contactsRemarkDao.add(contactsRemark);
            if(count3!=1){
                flag = false;
            }

            // 转成客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(createTime);
            customerRemark.setEditFlag("0");
            customerRemark.setNoteContent(clueRemark.getNoteContent());
            int count4 = customerRemarkDao.add(customerRemark);
            if(count4!=1){
                flag = false;
            }
        }

        // 5. “线索和市场活动”的关系转到“联系人和市场活动” 的关系
        List<ClueActivityRelation> clueActivityRelations = clueActivityRelationDao.selectBycId(cId);
        for(ClueActivityRelation clueActivityRelation:clueActivityRelations){
            String activityId = clueActivityRelation.getActivityId();
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(activityId);
            int count5 = contactsActivityRelationDao.add(contactsActivityRelation);
            if(count5!=1){
                flag = false;
            }
        }

        //6.如果有创见交易需求，则创建一条交易
        if(tran.getActivityId()!=null){
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(createBy);
            tran.setContactSummary(contacts.getContactSummary());
            tran.setDescription(clue.getDescription());
            tran.setCreateTime(createTime);
            tran.setCustomerId(customer.getId());
            tran.setId(UUIDUtil.getUUID());
            tran.setOwner(clue.getOwner());
            tran.setNextContactTime(clue.getNextContactTime());
            tran.setSource(clue.getSource());
            int count6 = tranDao.add(tran);
            if(count6!=1)
            {
                flag=false;
            }

            // 创建交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setCreateBy(createBy);
            tranHistory.setCreateTime(createTime);
            tranHistory.setTranId(tran.getId());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setStage(tran.getStage());
            int count7 = tranHistoryDao.add(tranHistory);
            if(count7!=1){
                flag=false;
            }
        }
        // 8，删除线索备注
        int count8 = clueRemarkDao.deleteBycId(cId);
        if(count8!=clueRemarks.size()){
            flag=false;
        }
        //9.删除线索和市场活动的关系
        int count9 = clueActivityRelationDao.deleteBycId(cId);
        if(count9!=clueActivityRelations.size()){
            flag = false;
        }

        //10.删除线索
        int count10 = clueDao.deleteById(cId);
        if(count10!=1){
            flag = false;
        }
        return flag;
    }
}
