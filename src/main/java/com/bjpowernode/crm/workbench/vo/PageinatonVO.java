package com.bjpowernode.crm.workbench.vo;

import java.util.List;

public class PageinatonVO<T> {
    private Integer totalSize;
    private List<T> dataList;

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
