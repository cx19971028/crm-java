package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {
    boolean add(Tran tran);

    Tran getTranById(String id);

    List<TranHistory> getHistory(String id);

    List<Map<String, Object>> getHistoryList();
}
