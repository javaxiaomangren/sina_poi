package com.tuan800.sinapoi.util;

/**
 * Task Executed status
 * @author Yang Hua
 */
public enum STATUS {
    READY("ready"),COMPLETED("completed"), TIMEOUT("timeout"),
    ERROR("error"), FAILED("failed"),CANCELLED("cancelled");

    private String desc ;

    private STATUS(String desc){
        this.desc = desc ;
    }

    public String getDesc() {
        return desc;
    }
}
