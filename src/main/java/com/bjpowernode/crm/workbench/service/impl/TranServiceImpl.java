package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {
    @Autowired
    private TranDao tranDao;
    @Autowired
    private TranHistoryDao tranHistoryDao;

    @Transactional
    @Override
    public boolean add(Tran tran) {
        boolean flag = true;
        int count1= tranDao.add(tran);
        if(count1!=1){
            flag=false;
        }
        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setCreateBy(tran.getCreateBy());

        int count2 = tranHistoryDao.add(tranHistory);
        if(count2 != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran getTranById(String id) {
        Tran t = tranDao.getTranById(id);
        return t;
    }

    @Override
    public List<TranHistory> getHistory(String id) {
        List<TranHistory> tranHistories = tranHistoryDao.getHistory(id);
        return tranHistories;
    }

    @Override
    public List<Map<String, Object>> getHistoryList() {
        List<Map<String, Object>> list = tranHistoryDao.getHistoryList();
        return list;
    }
}
