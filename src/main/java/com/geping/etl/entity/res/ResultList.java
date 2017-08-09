package com.geping.etl.entity.res;

import java.util.List;

/**
 * 
 * @author chenxuan
 *
 * @param <T>
 */
public class ResultList<T> extends Result {

    private List<T> itemList;

    public ResultList() {
        super();
    }
    
    public ResultList(List<T> itemList) {
        super();
        this.itemList = itemList;
    }

    public ResultList(Integer status, String message, List<T> itemList) {
        super(status, message);
        this.itemList = itemList;
    }

    public List<T> getItemList() {
        return itemList;
    }

    public void setItemList(List<T> itemList) {
        this.itemList = itemList;
    }
}



