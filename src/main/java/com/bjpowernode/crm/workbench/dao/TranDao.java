package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Tran;

public interface TranDao {

    int add(Tran tran);

    Tran getTranById(String id);
}
