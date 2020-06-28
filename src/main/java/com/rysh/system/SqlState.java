package com.rysh.system;

public enum  SqlState {

    DELETE("软删除",-1),
    DISABLE("禁用",0),
    ACTIVE("有效",1);

    private String state;
    private int stateCode;

    private SqlState(String state, int stateCode) {
        this.state = state;
        this.stateCode = stateCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }
}
