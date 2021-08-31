package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranHistoryDao {

    int add(TranHistory tranHistory);

    List<TranHistory> getHistory(String id);

    List<Map<String, Object>> getHistoryList();
}
