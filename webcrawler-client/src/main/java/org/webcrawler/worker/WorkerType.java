package org.webcrawler.worker;

public enum WorkerType {

    SYNC("-sync"), ASYNC("-async");

    private String param;
    WorkerType(String param){
        this.param = param;
    }
    public String getParam(){ return param;}

}
