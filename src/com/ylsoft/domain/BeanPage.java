package com.ylsoft.domain;

import java.util.List;

public class BeanPage {
    private int totalCount; // 总共多少条数据
    private int totalPage; // 总共多少页
    private List list;   // 每页显示的数据集合
    private int rows;   // 每页显示多少条数据
    private int currentPage;        // 当前是选中是第几页

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
