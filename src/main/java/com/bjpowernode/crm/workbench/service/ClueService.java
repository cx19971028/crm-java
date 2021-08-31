package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.domain.Tran;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ClueService {
    Map<String, Object> saveClue(Clue clue);

    Clue selectById(String id);

    List<Activity> SelectClueAndActivityRelation(String id);

    boolean removeRelation(String carId);

    boolean addRelation(List<ClueActivityRelation> clueActivityRelations);

    boolean convert(String cId, Tran tran, String createBy);
}
