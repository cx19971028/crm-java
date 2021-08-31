package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueDao {


    int saveClue(Clue clue);

    Clue selectById(String id);

    List<Activity> SelectClueAndActivityRelation(String id);

    int removeRelation(String carId);

    int deleteById(String cId);
}
