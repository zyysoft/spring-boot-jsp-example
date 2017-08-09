package com.geping.etl.entity.res;

public class ResultObject<T> extends Result {

    private T domain;
    
    public ResultObject() {
        super();
    }
    
    public ResultObject(T domain) {
        super();
        this.domain = domain;
    }

    public ResultObject(Integer status, String message, T domain) {
        super(status, message);
        this.domain = domain;
    }

    public T getDomain() {
        return domain;
    }
    
    public void setDomain(T domain) {
        this.domain = domain;
    }
}
