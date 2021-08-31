package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int addRelation(ClueActivityRelation c);

    List<ClueActivityRelation> selectBycId(String cId);

    int deleteBycId(String cId);
}
