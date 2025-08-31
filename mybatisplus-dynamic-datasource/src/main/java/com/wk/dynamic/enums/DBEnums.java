package com.wk.dynamic.enums;

public enum DBEnums {

    DB1("db1"),

    DB2("db2");

    private String value;

    DBEnums(String value) {
        this.value = value;
    }
    public String getValue(){
        return value;
    }

}
